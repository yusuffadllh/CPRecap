package com.yusuffdllh.smartfinance.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yusuffdllh.smartfinance.data.local.AppDatabase
import com.yusuffdllh.smartfinance.data.local.CategoryPresets
import com.yusuffdllh.smartfinance.data.local.DatabasePassphraseProvider
import com.yusuffdllh.smartfinance.data.local.dao.BudgetDao
import com.yusuffdllh.smartfinance.data.local.dao.CategoryDao
import com.yusuffdllh.smartfinance.data.local.dao.ScheduledBillDao
import com.yusuffdllh.smartfinance.data.local.dao.TransactionDao
import com.yusuffdllh.smartfinance.data.local.dao.TransactionDraftDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        categoryDaoProvider: Provider<CategoryDao>,
        applicationScope: CoroutineScope,
        passphraseProvider: DatabasePassphraseProvider
    ): AppDatabase {
        // Load the SQLCipher native library once before opening the database.
        System.loadLibrary("sqlcipher")

        // --- Opsi A: wipe legacy plaintext DB ---------------------------------
        // A previously-created database was UNENCRYPTED. SQLCipher cannot open a
        // plaintext file, so we delete any legacy DB before building the new,
        // encrypted one. The DB is then recreated empty and re-seeded via the
        // onCreate callback. (App is still versionCode 1 / in development.)
        deleteLegacyPlaintextDatabase(context, DB_NAME)

        val factory = SupportOpenHelperFactory(passphraseProvider.getOrCreatePassphrase())

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Seed default categories on first creation using the app-managed scope.
                    applicationScope.launch {
                        categoryDaoProvider.get().insertAll(CategoryPresets.categories)
                    }
                }
            })
            .build()
    }

    /**
     * Deletes a legacy UNENCRYPTED database (and its -wal/-shm sidecar files),
     * but only once. A marker preference ensures we don't repeatedly delete the
     * now-encrypted database on subsequent launches.
     */
    private fun deleteLegacyPlaintextDatabase(context: Context, dbName: String) {
        val marker = context.getSharedPreferences("db_migration", Context.MODE_PRIVATE)
        if (marker.getBoolean("encrypted_v1_done", false)) return

        val dbFile = context.getDatabasePath(dbName)
        if (dbFile.exists()) {
            context.deleteDatabase(dbName)
        }
        marker.edit().putBoolean("encrypted_v1_done", true).apply()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao

    @Provides
    @Singleton
    fun provideTransactionDraftDao(db: AppDatabase): TransactionDraftDao = db.transactionDraftDao

    @Provides
    @Singleton
    fun provideBudgetDao(db: AppDatabase): BudgetDao = db.budgetDao

    @Provides
    @Singleton
    fun provideCategoryDao(db: AppDatabase): CategoryDao = db.categoryDao

    @Provides
    @Singleton
    fun provideScheduledBillDao(db: AppDatabase): ScheduledBillDao = db.scheduledBillDao

    private const val DB_NAME = "smartfinance_db"
}
