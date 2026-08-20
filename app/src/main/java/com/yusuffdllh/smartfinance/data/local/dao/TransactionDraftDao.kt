package com.yusuffdllh.smartfinance.data.local.dao

import androidx.room.*
import com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDraftDao {
    @Query("SELECT * FROM transaction_drafts WHERE userId = :userId ORDER BY date DESC")
    fun getDraftsByUserId(userId: String): Flow<List<TransactionDraftEntity>>

    @Query("SELECT COUNT(*) FROM transaction_drafts WHERE userId = :userId")
    fun getDraftCount(userId: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: TransactionDraftEntity)

    @Delete
    suspend fun deleteDraft(draft: TransactionDraftEntity)

    @Query("DELETE FROM transaction_drafts WHERE userId = :userId")
    suspend fun deleteAllDraftsForUser(userId: String)

    @Query("SELECT * FROM transaction_drafts WHERE userId = :userId AND amount = :amount AND date > :timeLimit")
    suspend fun getRecentDraftsByAmount(userId: String, amount: Long, timeLimit: Long): List<TransactionDraftEntity>

    @Query("SELECT COUNT(*) FROM transaction_drafts WHERE userId = :userId AND amount = :amount AND type = :type AND date > :timeLimit")
    suspend fun checkRecentByAmountAndType(userId: String, amount: Long, type: String, timeLimit: Long): Int
}
