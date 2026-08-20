package com.yusuffdllh.smartfinance.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import com.yusuffdllh.smartfinance.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val authRepository: com.yusuffdllh.smartfinance.domain.repository.AuthRepository,
    private val draftDao: com.yusuffdllh.smartfinance.data.local.dao.TransactionDraftDao
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filterType = MutableStateFlow("Semua")
    val filterType: StateFlow<String> = _filterType.asStateFlow()

    val transactions: StateFlow<List<TransactionEntity>> = combine(
        transactionRepository.getTransactions(),
        _searchQuery,
        _filterType
    ) { txs, query, filter ->
        txs.filter { transaction ->
            val searchMatch = transaction.title.contains(query, ignoreCase = true)
            val filterMatch = when (filter) {
                "Pemasukan" -> transaction.type == "INCOME"
                "Pengeluaran" -> transaction.type == "EXPENSE"
                else -> true
            }
            searchMatch && filterMatch
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val drafts: StateFlow<List<com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity>> = authRepository.currentUser
        .flatMapLatest { user ->
            if (user != null) draftDao.getDraftsByUserId(user.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onFilterTypeChanged(filter: String) {
        _filterType.value = filter
    }

    fun confirmDraft(draft: com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity) {
        viewModelScope.launch {
            val transaction = TransactionEntity(
                userId = draft.userId,
                title = draft.merchant,
                amount = draft.amount,
                category = draft.category,
                date = draft.date,
                type = draft.type,
                note = "Dikonfirmasi dari draf"
            )
            transactionRepository.addTransaction(transaction)
            draftDao.deleteDraft(draft)
        }
    }

    fun deleteDraft(draft: com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity) {
        viewModelScope.launch {
            draftDao.deleteDraft(draft)
        }
    }

    fun confirmAllDrafts() {
        viewModelScope.launch {
            drafts.value.forEach { draft ->
                val transaction = TransactionEntity(
                    userId = draft.userId,
                    title = draft.merchant,
                    amount = draft.amount,
                    category = draft.category,
                    date = draft.date,
                    type = draft.type,
                    note = "Dikonfirmasi masal"
                )
                transactionRepository.addTransaction(transaction)
                draftDao.deleteDraft(draft)
            }
        }
    }
}
