package com.yusuffdllh.smartfinance.domain.repository

import com.yusuffdllh.smartfinance.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getAllBudgets(): Flow<List<BudgetEntity>>
    suspend fun updateBudget(budget: BudgetEntity): Result<Unit>
    suspend fun deleteBudget(budget: BudgetEntity): Result<Unit>

    /** Pulls the user's budgets from Firestore into the local Room cache. */
    suspend fun restoreFromFirestore(userId: String): Result<Unit>
}
