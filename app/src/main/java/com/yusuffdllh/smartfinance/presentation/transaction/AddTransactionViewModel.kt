package com.yusuffdllh.smartfinance.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import com.yusuffdllh.smartfinance.data.model.Category
import com.yusuffdllh.smartfinance.domain.repository.AuthRepository
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import com.yusuffdllh.smartfinance.domain.usecase.GetCategoriesUseCase
import com.yusuffdllh.smartfinance.service.AiService
import com.yusuffdllh.smartfinance.service.RuleEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val authRepository: AuthRepository,
    private val ruleEngine: RuleEngine,
    private val aiService: AiService,
    getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddTransactionUiState>(AddTransactionUiState.Idle)
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    private val _predictedCategory = MutableStateFlow("Umum")
    val predictedCategory: StateFlow<String> = _predictedCategory.asStateFlow()

    val expenseCategories: StateFlow<List<Category>> = getCategoriesUseCase.byType(isIncome = false)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val incomeCategories: StateFlow<List<Category>> = getCategoriesUseCase.byType(isIncome = true)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private var editingTransactionId: Long? = null

    fun resetEditingState() {
        editingTransactionId = null
    }

    fun loadTransaction(id: Long, onLoaded: (TransactionEntity) -> Unit) {
        viewModelScope.launch {
            val transaction = transactionRepository.getTransactionById(id)
            transaction?.let {
                editingTransactionId = it.id
                onLoaded(it)
            }
        }
    }

    fun onTitleChanged(title: String, amount: Long = 0) {
        viewModelScope.launch {
            // 1. Try Rule Engine (Offline)
            val ruleResult = ruleEngine.predictCategory(title)
            if (ruleResult != null) {
                _predictedCategory.value = ruleResult
            } else {
                // 2. Try Gemini AI (Fallback)
                if (title.length > 3) {
                    val aiResult = aiService.predictCategory(title, amount)
                    _predictedCategory.value = aiResult
                }
            }
        }
    }

    fun addTransaction(
        title: String,
        amount: Long,
        category: String,
        type: String,
        date: Long? = null,
        note: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = AddTransactionUiState.Loading
            val user = authRepository.currentUser.first()
            if (user != null) {
                val baseTransaction = TransactionEntity(
                    userId = user.id,
                    title = title,
                    amount = amount,
                    category = category,
                    date = date ?: System.currentTimeMillis(),
                    type = type,
                    note = note
                )

                val transaction = if (editingTransactionId != null) {
                    baseTransaction.copy(id = editingTransactionId!!)
                } else {
                    baseTransaction
                }

                val result = transactionRepository.addTransaction(transaction)
                if (result.isSuccess) {
                    _uiState.value = AddTransactionUiState.Success
                } else {
                    _uiState.value = AddTransactionUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                }
            } else {
                _uiState.value = AddTransactionUiState.Error("User not logged in")
            }
        }
    }

    fun deleteTransaction(onDeleted: () -> Unit) {
        val transactionId = editingTransactionId ?: return
        viewModelScope.launch {
            _uiState.value = AddTransactionUiState.Loading
            val user = authRepository.currentUser.first()
            if (user != null) {
                // We need to fetch the full entity to delete it as the repository usually needs the entity
                val transaction = transactionRepository.getTransactionById(transactionId)
                if (transaction != null) {
                    val result = transactionRepository.deleteTransaction(transaction)
                    if (result.isSuccess) {
                        onDeleted()
                    } else {
                        _uiState.value = AddTransactionUiState.Error(result.exceptionOrNull()?.message ?: "Gagal menghapus")
                    }
                }
            }
        }
    }
}

sealed class AddTransactionUiState {
    object Idle : AddTransactionUiState()
    object Loading : AddTransactionUiState()
    object Success : AddTransactionUiState()
    data class Error(val message: String) : AddTransactionUiState()
}
