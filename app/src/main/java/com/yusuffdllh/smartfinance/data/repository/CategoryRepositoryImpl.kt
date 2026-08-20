package com.yusuffdllh.smartfinance.data.repository

import com.yusuffdllh.smartfinance.data.local.CategoryPresets
import com.yusuffdllh.smartfinance.data.local.dao.CategoryDao
import com.yusuffdllh.smartfinance.data.mapper.CategoryMapper
import com.yusuffdllh.smartfinance.data.model.Category
import com.yusuffdllh.smartfinance.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories().map { CategoryMapper.toDomainList(it) }

    override fun getCategoriesByType(isIncome: Boolean): Flow<List<Category>> =
        categoryDao.getCategoriesByType(isIncome).map { CategoryMapper.toDomainList(it) }

    override suspend fun getCategoryByName(name: String): Category? =
        categoryDao.getCategoryByName(name)?.let { CategoryMapper.toDomain(it) }

    override suspend fun ensureSeeded() {
        if (categoryDao.count() == 0) {
            categoryDao.insertAll(CategoryPresets.categories)
        }
    }
}
