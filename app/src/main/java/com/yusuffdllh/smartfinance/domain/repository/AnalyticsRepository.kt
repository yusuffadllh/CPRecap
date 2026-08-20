package com.yusuffdllh.smartfinance.domain.repository

import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {
    fun getCategoryBreakdown(): Flow<Map<String, Long>>
    fun getMonthlyTrend(): Flow<List<Pair<Long, Long>>> // Timestamp to Amount
}
