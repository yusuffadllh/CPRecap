package com.yusuffdllh.smartfinance.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yusuffdllh.smartfinance.data.local.AppDatabase
import com.yusuffdllh.smartfinance.data.local.CategoryPresets
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
        applicationScope: CoroutineScope
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "smartfinance_db"
        ).fallbackToDestructiveMigration()
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
}
