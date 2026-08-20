package com.yusuffdllh.smartfinance.domain.repository

import com.yusuffdllh.smartfinance.data.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoriesByType(isIncome: Boolean): Flow<List<Category>>
    suspend fun getCategoryByName(name: String): Category?

    /** Seeds the default preset categories if the store is empty. */
    suspend fun ensureSeeded()
}
