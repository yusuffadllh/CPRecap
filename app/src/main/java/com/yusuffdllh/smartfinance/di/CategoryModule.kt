package com.yusuffdllh.smartfinance.di

import com.yusuffdllh.smartfinance.data.repository.CategoryRepositoryImpl
import com.yusuffdllh.smartfinance.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository
}
