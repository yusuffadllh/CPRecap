package com.yusuffdllh.smartfinance.data.local.dao

import androidx.room.*
import com.yusuffdllh.smartfinance.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets WHERE userId = :userId")
    fun getBudgetsByUserId(userId: String): Flow<List<BudgetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity)

    @Delete
    suspend fun deleteBudget(budget: BudgetEntity)

    @Query("DELETE FROM budgets WHERE userId = :userId")
    suspend fun deleteAllBudgetsForUser(userId: String)
}
