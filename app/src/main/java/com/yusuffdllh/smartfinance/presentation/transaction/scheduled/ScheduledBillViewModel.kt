package com.yusuffdllh.smartfinance.presentation.transaction.scheduled

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.data.local.entity.ScheduledBillEntity
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.ScheduledBillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledBillViewModel @Inject constructor(
    private val repository: ScheduledBillRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScheduledBillUiState>(ScheduledBillUiState.Idle)
    val uiState: StateFlow<ScheduledBillUiState> = _uiState.asStateFlow()

    val bills: StateFlow<List<ScheduledBillEntity>> = repository.getBills()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addBill(name: String, amount: Long, category: String, dueDate: Int, isAutoPaid: Boolean) {
        viewModelScope.launch {
            val user = authRepository.currentUser.first()
            if (user != null) {
                val bill = ScheduledBillEntity(
                    userId = user.id,
                    name = name,
                    amount = amount,
                    category = category,
                    dueDate = dueDate,
                    isAutoPaid = isAutoPaid
                )
                repository.addBill(bill)
            }
        }
    }

    fun deleteBill(bill: ScheduledBillEntity) {
        viewModelScope.launch {
            repository.deleteBill(bill)
        }
    }
}

sealed class ScheduledBillUiState {
    object Idle : ScheduledBillUiState()
    object Loading : ScheduledBillUiState()
    object Success : ScheduledBillUiState()
    data class Error(val message: String) : ScheduledBillUiState()
}
