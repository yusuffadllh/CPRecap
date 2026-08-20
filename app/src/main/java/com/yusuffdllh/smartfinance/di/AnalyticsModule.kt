package com.yusuffdllh.smartfinance.di

import com.yusuffdllh.smartfinance.data.repository.AnalyticsRepositoryImpl
import com.yusuffdllh.smartfinance.domain.repository.AnalyticsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsRepository(
        analyticsRepositoryImpl: AnalyticsRepositoryImpl
    ): AnalyticsRepository
}
