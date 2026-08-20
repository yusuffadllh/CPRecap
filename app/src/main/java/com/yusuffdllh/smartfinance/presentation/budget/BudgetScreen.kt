package com.yusuffdllh.smartfinance.presentation.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.components.BudgetProgress
import com.yusuffdllh.smartfinance.presentation.dashboard.DashboardViewModel
import com.yusuffdllh.smartfinance.presentation.dashboard.components.DashboardBottomBar
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun BudgetScreen(
    navController: NavController,
    viewModel: BudgetViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val budgets by viewModel.budgets.collectAsState()
    val allTransactions by dashboardViewModel.allTransactions.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var newCategory by remember { mutableStateOf("") }
    var newAmount by remember { mutableStateOf("") }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Atur Anggaran Baru") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newCategory,
                        onValueChange = { newCategory = it },
                        label = { Text("Kategori") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newAmount,
                        onValueChange = { newAmount = it },
                        label = { Text("Limit Anggaran") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.addBudget(newCategory, newAmount.toLongOrNull() ?: 0L)
                    showAddDialog = false
                    newCategory = ""
                    newAmount = ""
                }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        bottomBar = { DashboardBottomBar(navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = "Anggaran Bulanan",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            if (budgets.isEmpty()) {
                Text(text = "Belum ada anggaran yang diatur. Tekan tombol + untuk membuat.", color = TextPrimary)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    items(budgets) { budget ->
                        val spent = allTransactions
                            .filter { it.category == budget.category && it.type == "EXPENSE" }
                            .sumOf { it.amount }
                        
                        BudgetProgress(
                            category = budget.category,
                            spent = spent,
                            total = budget.amount
                        )
                    }
                }
            }
        }
    }
}
