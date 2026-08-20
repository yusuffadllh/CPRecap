package com.yusuffdllh.smartfinance.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.yusuffdllh.smartfinance.data.local.dao.BudgetDao
import com.yusuffdllh.smartfinance.data.local.entity.BudgetEntity
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.BudgetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepositoryImpl @Inject constructor(
    private val budgetDao: BudgetDao,
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore,
    private val externalScope: CoroutineScope
) : BudgetRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllBudgets(): Flow<List<BudgetEntity>> {
        return authRepository.currentUser.flatMapLatest { user ->
            if (user != null) {
                budgetDao.getBudgetsByUserId(user.id)
            } else {
                flowOf(emptyList())
            }
        }
    }

    override suspend fun updateBudget(budget: BudgetEntity): Result<Unit> {
        return try {
            budgetDao.insertBudget(budget)
            syncToFirestore(budget)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "updateBudget failed", e)
            Result.failure(e)
        }
    }

    private fun syncToFirestore(budget: BudgetEntity) {
        externalScope.launch {
            try {
                firestore.collection("users")
                    .document(budget.userId)
                    .collection("budgets")
                    .document(budget.category)
                    .set(budget)
                    .await()
                
                budgetDao.insertBudget(budget.copy(isSynced = true))
            } catch (e: Exception) {
                // Background sync failure is non-fatal; keep local data and log for diagnostics.
                Log.w(TAG, "Failed to sync budget to Firestore", e)
            }
        }
    }

    override suspend fun deleteBudget(budget: BudgetEntity): Result<Unit> {
        return try {
            budgetDao.deleteBudget(budget)
            externalScope.launch {
                try {
                    firestore.collection("users")
                        .document(budget.userId)
                        .collection("budgets")
                        .document(budget.category)
                        .delete()
                        .await()
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to delete budget from Firestore", e)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "deleteBudget failed", e)
            Result.failure(e)
        }
    }

    override suspend fun restoreFromFirestore(userId: String): Result<Unit> {
        if (userId.isEmpty()) return Result.success(Unit)
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("budgets")
                .get()
                .await()
            val remote = snapshot.documents.mapNotNull { it.toObject(BudgetEntity::class.java) }
            remote.forEach { budgetDao.insertBudget(it.copy(isSynced = true)) }
            Log.i(TAG, "Restored ${remote.size} budgets from Firestore")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.w(TAG, "restoreFromFirestore failed", e)
            Result.failure(e)
        }
    }

    private companion object {
        const val TAG = "BudgetRepository"
    }
}
