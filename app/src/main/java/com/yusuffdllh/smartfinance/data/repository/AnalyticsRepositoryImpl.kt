package com.yusuffdllh.smartfinance.data.repository

import com.yusuffdllh.smartfinance.domain.repository.AnalyticsRepository
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsRepositoryImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
) : AnalyticsRepository {

    override fun getCategoryBreakdown(): Flow<Map<String, Long>> {
        return transactionRepository.getTransactions().map { transactions ->
            transactions
                .filter { it.type == "EXPENSE" }
                .groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }
        }
    }

    override fun getMonthlyTrend(): Flow<List<Pair<Long, Long>>> {
        return transactionRepository.getTransactions().map { transactions ->
            transactions.groupBy { 
                // Group by start of day or similar for trend
                it.date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000)
            }
            .map { it.key to it.value.sumOf { t -> t.amount } }
            .sortedBy { it.first }
        }
    }
}
