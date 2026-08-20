package com.yusuffdllh.smartfinance.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yusuffdllh.smartfinance.data.local.dao.BudgetDao
import com.yusuffdllh.smartfinance.data.local.dao.CategoryDao
import com.yusuffdllh.smartfinance.data.local.dao.ScheduledBillDao
import com.yusuffdllh.smartfinance.data.local.dao.TransactionDao
import com.yusuffdllh.smartfinance.data.local.dao.TransactionDraftDao
import com.yusuffdllh.smartfinance.data.local.entity.BudgetEntity
import com.yusuffdllh.smartfinance.data.local.entity.CategoryEntity
import com.yusuffdllh.smartfinance.data.local.entity.ScheduledBillEntity
import com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, CategoryEntity::class, BudgetEntity::class, TransactionDraftEntity::class, ScheduledBillEntity::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    abstract val transactionDraftDao: TransactionDraftDao
    abstract val budgetDao: BudgetDao
    abstract val categoryDao: CategoryDao
    abstract val scheduledBillDao: ScheduledBillDao
}
