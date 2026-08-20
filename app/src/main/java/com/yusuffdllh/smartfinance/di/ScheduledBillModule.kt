package com.yusuffdllh.smartfinance.di

import com.yusuffdllh.smartfinance.data.repository.ScheduledBillRepositoryImpl
import com.yusuffdllh.smartfinance.domain.repository.ScheduledBillRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduledBillModule {

    @Binds
    @Singleton
    abstract fun bindScheduledBillRepository(
        scheduledBillRepositoryImpl: ScheduledBillRepositoryImpl
    ): ScheduledBillRepository
}
