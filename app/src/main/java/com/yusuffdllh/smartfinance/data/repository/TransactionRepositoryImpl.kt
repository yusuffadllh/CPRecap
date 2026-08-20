package com.yusuffdllh.smartfinance.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.yusuffdllh.smartfinance.data.local.dao.TransactionDao
import com.yusuffdllh.smartfinance.data.local.dao.TransactionDraftDao
import com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val draftDao: TransactionDraftDao,
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore,
    private val externalScope: CoroutineScope
) : TransactionRepository {

    private val mutex = Mutex()

    private suspend fun isDuplicate(userId: String, amount: Long, type: String): Boolean {
        // Simple, reliable shield: same amount + same type within 10 minutes
        val timeLimit = System.currentTimeMillis() - (10 * 60000)
        val txCount = transactionDao.checkRecentByAmountAndType(userId, amount, type, timeLimit)
        val draftCount = draftDao.checkRecentByAmountAndType(userId, amount, type, timeLimit)
        return (txCount + draftCount) > 0
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTransactions(): Flow<List<TransactionEntity>> {
        return authRepository.currentUser.flatMapLatest { user ->
            if (user != null) transactionDao.getTransactionsByUserId(user.id)
            else flowOf(emptyList())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTotalIncome(): Flow<Long> {
        return authRepository.currentUser.flatMapLatest { user ->
            if (user != null) transactionDao.getTotalIncome(user.id).map { it ?: 0L }
            else flowOf(0L)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTotalExpense(): Flow<Long> {
        return authRepository.currentUser.flatMapLatest { user ->
            if (user != null) transactionDao.getTotalExpense(user.id).map { it ?: 0L }
            else flowOf(0L)
        }
    }

    override suspend fun getTransactionById(id: Long): TransactionEntity? {
        return transactionDao.getTransactionById(id)
    }

    override suspend fun addTransaction(transaction: TransactionEntity): Result<Unit> {
        return mutex.withLock {
            try {
                // Only guard against duplicates for brand-new inserts (id == 0).
                // For updates (edits), the amount/type may legitimately be unchanged,
                // so the duplicate shield must NOT block the update.
                val isUpdate = transaction.id != 0L
                if (!isUpdate && isDuplicate(transaction.userId, transaction.amount, transaction.type)) {
                    return@withLock Result.success(Unit)
                }
                val newId = transactionDao.insertTransaction(transaction)
                val savedId = if (isUpdate) transaction.id else newId
                syncToFirestore(transaction.copy(id = savedId))
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to add transaction", e)
                Result.failure(e)
            }
        }
    }

    override suspend fun addDraft(draft: TransactionDraftEntity): Result<Unit> {
        return mutex.withLock {
            try {
                if (isDuplicate(draft.userId, draft.amount, draft.type)) {
                    return@withLock Result.success(Unit)
                }
                draftDao.insertDraft(draft)
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to add draft", e)
                Result.failure(e)
            }
        }
    }

    override suspend fun deleteDraft(draft: TransactionDraftEntity): Result<Unit> {
        return try {
            draftDao.deleteDraft(draft)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete draft", e)
            Result.failure(e)
        }
    }

    private fun syncToFirestore(transaction: TransactionEntity) {
        if (transaction.userId.isEmpty()) return
        externalScope.launch {
            try {
                firestore.collection("users")
                    .document(transaction.userId)
                    .collection("transactions")
                    .document(transaction.id.toString())
                    .set(transaction)
                    .await()
                transactionDao.insertTransaction(transaction.copy(isSynced = true))
            } catch (e: Exception) {
                // Background sync failure is non-fatal; local data is authoritative.
                Log.w(TAG, "Failed to sync transaction to Firestore (id=${transaction.id})", e)
            }
        }
    }

    override suspend fun deleteTransaction(transaction: TransactionEntity): Result<Unit> {
        return try {
            transactionDao.deleteTransaction(transaction)
            externalScope.launch {
                try {
                    firestore.collection("users")
                        .document(transaction.userId)
                        .collection("transactions")
                        .document(transaction.id.toString())
                        .delete()
                        .await()
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to delete transaction from Firestore (id=${transaction.id})", e)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete transaction", e)
            Result.failure(e)
        }
    }

    override suspend fun restoreFromFirestore(userId: String): Result<Unit> {
        if (userId.isEmpty()) return Result.success(Unit)
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("transactions")
                .get()
                .await()
            val remote = snapshot.documents.mapNotNull { it.toObject(TransactionEntity::class.java) }
            // Mark as synced since they came from the cloud; REPLACE keeps ids stable.
            remote.forEach { transactionDao.insertTransaction(it.copy(isSynced = true)) }
            Log.i(TAG, "Restored ${remote.size} transactions from Firestore")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.w(TAG, "restoreFromFirestore failed", e)
            Result.failure(e)
        }
    }

    private companion object {
        const val TAG = "TransactionRepository"
    }
}