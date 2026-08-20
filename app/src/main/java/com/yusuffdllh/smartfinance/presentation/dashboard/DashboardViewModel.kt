package com.yusuffdllh.smartfinance.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.BudgetRepository
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val transactionRepository: TransactionRepository,
    private val budgetRepository: BudgetRepository,
    private val draftDao: com.yusuffdllh.smartfinance.data.local.dao.TransactionDraftDao
) : ViewModel() {

    private val _selectedMonth = MutableStateFlow(getCurrentMonth())
    val selectedMonth: StateFlow<String> = _selectedMonth.asStateFlow()

    val allTransactions = transactionRepository.getTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val monthlyTransactions = combine(allTransactions, _selectedMonth) { txs, month ->
        txs.filter { formatMonth(it.date) == month }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val budgets = budgetRepository.getAllBudgets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val userName: StateFlow<String> = authRepository.currentUser
        .map { it?.name ?: "User" }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "User"
        )

    val totalIncome: StateFlow<Long> = transactionRepository.getTotalIncome()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0L
        )

    val totalExpense: StateFlow<Long> = transactionRepository.getTotalExpense()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0L
        )

    val totalBalance: StateFlow<Long> = combine(
        transactionRepository.getTotalIncome(),
        transactionRepository.getTotalExpense()
    ) { income, expense ->
        (income ?: 0L) - (expense ?: 0L)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0L
    )

    val recentTransactions = transactionRepository.getTransactions()
        .map { it.take(5) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val pendingDraftsCount: StateFlow<Int> = authRepository.currentUser.flatMapLatest { user ->
        if (user != null) draftDao.getDraftCount(user.id) else flowOf(0)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun selectMonth(month: String) {
        _selectedMonth.value = month
    }

    private fun getCurrentMonth(): String {
        return try {
            SimpleDateFormat("MMMM yyyy", Locale("id", "ID")).format(Date())
        } catch (e: Exception) {
            SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
        }
    }

    private fun formatMonth(timestamp: Long): String {
        return try {
            SimpleDateFormat("MMMM yyyy", Locale("id", "ID")).format(Date(timestamp))
        } catch (e: Exception) {
            SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date(timestamp))
        }
    }
}
