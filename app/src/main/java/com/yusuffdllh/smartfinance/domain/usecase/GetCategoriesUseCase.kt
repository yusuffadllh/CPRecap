package com.yusuffdllh.smartfinance.domain.usecase

import com.yusuffdllh.smartfinance.data.model.Category
import com.yusuffdllh.smartfinance.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Provides category data to the presentation layer while guaranteeing the default
 * preset is seeded before the first emission.
 */
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    fun all(): Flow<List<Category>> =
        categoryRepository.getAllCategories().onStart { categoryRepository.ensureSeeded() }

    fun byType(isIncome: Boolean): Flow<List<Category>> =
        categoryRepository.getCategoriesByType(isIncome).onStart { categoryRepository.ensureSeeded() }

    suspend fun byName(name: String): Category? = categoryRepository.getCategoryByName(name)
}
