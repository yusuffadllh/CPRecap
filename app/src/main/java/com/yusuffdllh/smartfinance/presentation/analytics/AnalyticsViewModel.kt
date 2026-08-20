package com.yusuffdllh.smartfinance.presentation.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _selectedPeriod = MutableStateFlow("Bulanan")
    val selectedPeriod: StateFlow<String> = _selectedPeriod.asStateFlow()

    private val _selectedMonth = MutableStateFlow(getCurrentMonth())
    val selectedMonth: StateFlow<String> = _selectedMonth.asStateFlow()

    private val _selectedDate = MutableStateFlow(System.currentTimeMillis())
    val selectedDate: StateFlow<Long> = _selectedDate.asStateFlow()

    val filteredTransactions = combine(
        transactionRepository.getTransactions(),
        _selectedPeriod,
        _selectedMonth,
        _selectedDate
    ) { txs, period, month, date ->
        when (period) {
            "Harian" -> txs.filter { isSameDay(it.date, date) }
            "Mingguan" -> txs.filter { isSameWeek(it.date, date) }
            "Bulanan" -> txs.filter { formatMonth(it.date) == month }
            "Tahunan" -> txs.filter { formatYear(it.date) == formatYear(date) }
            else -> txs
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val categoryBreakdown: StateFlow<Map<String, Long>> = filteredTransactions.map { txs ->
        txs.filter { it.type == "EXPENSE" }
            .groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )

    val monthlyTrend: StateFlow<List<Triple<Long, Long, Long>>> = filteredTransactions.map { txs ->
        txs.groupBy { it.date / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000) }
            .map { entry ->
                val income = entry.value.filter { it.type == "INCOME" }.sumOf { it.amount }
                val expense = entry.value.filter { it.type == "EXPENSE" }.sumOf { it.amount }
                Triple(entry.key, income, expense)
            }
            .sortedBy { it.first }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectPeriod(period: String) {
        _selectedPeriod.value = period
    }

    fun selectMonth(month: String) {
        _selectedMonth.value = month
    }

    fun selectDate(date: Long) {
        _selectedDate.value = date
        _selectedPeriod.value = "Harian"
    }

    private fun getCurrentMonth(): String = try {
        SimpleDateFormat("MMMM yyyy", Locale("id", "ID")).format(Date())
    } catch (e: Exception) {
        SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
    }

    private fun formatMonth(ts: Long): String = try {
        SimpleDateFormat("MMMM yyyy", Locale("id", "ID")).format(Date(ts))
    } catch (e: Exception) {
        SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date(ts))
    }

    private fun formatYear(ts: Long): String = try {
        SimpleDateFormat("yyyy", Locale("id", "ID")).format(Date(ts))
    } catch (e: Exception) {
        SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(ts))
    }
    
    private fun isSameDay(ts1: Long, ts2: Long): Boolean {
        val fmt = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return fmt.format(Date(ts1)) == fmt.format(Date(ts2))
    }

    private fun isSameWeek(ts1: Long, ts2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = ts1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = ts2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)
    }
}
