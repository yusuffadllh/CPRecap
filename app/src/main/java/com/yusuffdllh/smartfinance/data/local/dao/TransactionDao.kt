package com.yusuffdllh.smartfinance.data.local.dao

import androidx.room.*
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY date DESC")
    fun getTransactionsByUserId(userId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("SELECT SUM(amount) FROM transactions WHERE userId = :userId AND type = 'INCOME'")
    fun getTotalIncome(userId: String): Flow<Long?>

    @Query("SELECT SUM(amount) FROM transactions WHERE userId = :userId AND type = 'EXPENSE'")
    fun getTotalExpense(userId: String): Flow<Long?>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND amount = :amount AND date > :timeLimit")
    suspend fun getRecentTransactionsByAmount(userId: String, amount: Long, timeLimit: Long): List<TransactionEntity>

    @Query("SELECT COUNT(*) FROM transactions WHERE userId = :userId AND amount = :amount AND type = :type AND date > :timeLimit")
    suspend fun checkRecentByAmountAndType(userId: String, amount: Long, type: String, timeLimit: Long): Int
}
