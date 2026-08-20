package com.yusuffdllh.smartfinance.presentation.transaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.presentation.transaction.components.*
import com.yusuffdllh.smartfinance.ui.theme.Background
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    transactionId: Long? = null,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val predictedCategory by viewModel.predictedCategory.collectAsState()
    val expenseCategories by viewModel.expenseCategories.collectAsState()
    val incomeCategories by viewModel.incomeCategories.collectAsState()

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isIncome by remember { mutableStateOf(false) }
    var date by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(transactionId) {
        if (transactionId != null) {
            viewModel.loadTransaction(transactionId) { tx ->
                title = tx.title
                amount = tx.amount.toString()
                category = tx.category
                isIncome = tx.type == "INCOME"
                date = tx.date
            }
        } else {
            viewModel.resetEditingState()
            // Reset local fields
            title = ""
            amount = ""
            category = ""
            isIncome = false
            date = System.currentTimeMillis()
        }
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var showCategorySheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val nameBringIntoView = remember { BringIntoViewRequester() }
    val amountBringIntoView = remember { BringIntoViewRequester() }

    val canSave = title.isNotBlank() && 
                  (amount.toLongOrNull() ?: 0L) > 0 && 
                  category.isNotEmpty()

    LaunchedEffect(predictedCategory) {
        if (category.isEmpty() && predictedCategory != "Umum") {
            category = predictedCategory
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is AddTransactionUiState.Success) {
            navController.navigate(Screen.Success.route) {
                popUpTo(Screen.AddTransaction.route) { inclusive = true }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = {
                date = it
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showCategorySheet) {
        val categories = if (isIncome) incomeCategories else expenseCategories
        CategoryBottomSheet(
            selectedCategory = category,
            categories = categories,
            onDismiss = { showCategorySheet = false },
            onCategorySelected = {
                category = it
                showCategorySheet = false
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Transaksi?") },
            text = { Text("Transaksi ini akan dihapus secara permanen dari riwayat kamu.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteTransaction {
                            navController.popBackStack()
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = com.yusuffdllh.smartfinance.ui.theme.Danger)
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            },
            containerColor = com.yusuffdllh.smartfinance.ui.theme.Surface
        )
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            AddTransactionHeader(
                title = if (transactionId != null) "Ubah Transaksi" else "Tambah Transaksi",
                onBackClick = { navController.popBackStack() },
                onDeleteClick = if (transactionId != null) { { showDeleteDialog = true } } else null
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            TransactionTypeSelector(
                selectedIncome = isIncome,
                onTypeChanged = { 
                    isIncome = it 
                    category = "" // Reset category when type changes
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 1. Nama
            NameField(
                value = title,
                onValueChange = {
                    title = it
                    viewModel.onTitleChanged(it, amount.toLongOrNull() ?: 0L)
                },
                modifier = Modifier
                    .bringIntoViewRequester(nameBringIntoView)
                    .onFocusEvent { state ->
                        if (state.isFocused) {
                            coroutineScope.launch { nameBringIntoView.bringIntoView() }
                        }
                    }
            )
            
            Spacer(modifier = Modifier.height(18.dp))
            
            // 2. Kategori
            CategoryDropdown(
                selectedCategory = category,
                onClick = { showCategorySheet = true }
            )
            
            Spacer(modifier = Modifier.height(18.dp))
            
            // 3. Nominal
            AmountField(
                amount = amount,
                onAmountChange = {
                    amount = it
                    viewModel.onTitleChanged(title, it.toLongOrNull() ?: 0L)
                },
                modifier = Modifier
                    .bringIntoViewRequester(amountBringIntoView)
                    .onFocusEvent { state ->
                        if (state.isFocused) {
                            coroutineScope.launch { amountBringIntoView.bringIntoView() }
                        }
                    }
            )
            
            Spacer(modifier = Modifier.height(18.dp))
            
            // 4. Tanggal
            DateField(
                date = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")).format(Date(date)),
                onClick = { showDatePicker = true }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))
            
            if (uiState is AddTransactionUiState.Loading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                SaveTransactionButton(
                    selectedIncome = isIncome,
                    enabled = canSave,
                    onClick = {
                        viewModel.addTransaction(
                            title = title,
                            amount = amount.toLongOrNull() ?: 0L,
                            category = category,
                            type = if (isIncome) "INCOME" else "EXPENSE",
                            date = date,
                            note = null
                        )
                    }
                )
            }
            
            if (uiState is AddTransactionUiState.Error) {
                Text(
                    text = (uiState as AddTransactionUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
