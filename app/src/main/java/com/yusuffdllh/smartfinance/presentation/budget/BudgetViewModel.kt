package com.yusuffdllh.smartfinance.presentation.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.data.local.entity.BudgetEntity
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val budgets: StateFlow<List<BudgetEntity>> = budgetRepository.getAllBudgets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addBudget(category: String, amount: Long) {
        viewModelScope.launch {
            val user = authRepository.currentUser.first()
            if (user != null) {
                budgetRepository.updateBudget(
                    BudgetEntity(
                        userId = user.id,
                        category = category,
                        amount = amount,
                        period = "MONTHLY"
                    )
                )
            }
        }
    }
}
