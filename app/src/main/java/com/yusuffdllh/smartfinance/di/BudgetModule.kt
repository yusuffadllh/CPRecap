package com.yusuffdllh.smartfinance.di

import com.yusuffdllh.smartfinance.data.repository.BudgetRepositoryImpl
import com.yusuffdllh.smartfinance.domain.repository.BudgetRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BudgetModule {

    @Binds
    @Singleton
    abstract fun bindBudgetRepository(
        budgetRepositoryImpl: BudgetRepositoryImpl
    ): BudgetRepository
}
