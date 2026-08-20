package com.yusuffdllh.smartfinance.domain.repository

import com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(): Flow<List<TransactionEntity>>
    fun getTotalIncome(): Flow<Long>
    fun getTotalExpense(): Flow<Long>
    suspend fun getTransactionById(id: Long): TransactionEntity?
    suspend fun addTransaction(transaction: TransactionEntity): Result<Unit>
    suspend fun deleteTransaction(transaction: TransactionEntity): Result<Unit>

    /** Pulls the user's transactions from Firestore into the local Room cache. */
    suspend fun restoreFromFirestore(userId: String): Result<Unit>
    
    // Unified Draft Management
    suspend fun addDraft(draft: TransactionDraftEntity): Result<Unit>
    suspend fun deleteDraft(draft: TransactionDraftEntity): Result<Unit>
}
