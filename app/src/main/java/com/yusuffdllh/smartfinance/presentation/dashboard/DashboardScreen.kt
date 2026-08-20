package com.yusuffdllh.smartfinance.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.components.MonthPickerBottomSheet
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.presentation.dashboard.components.*
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsState()
    val totalBalance by viewModel.totalBalance.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()
    val recentTransactions by viewModel.recentTransactions.collectAsState()
    val monthlyTransactions by viewModel.monthlyTransactions.collectAsState()
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    val pendingDraftsCount by viewModel.pendingDraftsCount.collectAsState()

    var showMonthPicker by remember { mutableStateOf(false) }

    if (showMonthPicker) {
        MonthPickerBottomSheet(
            onDismiss = { showMonthPicker = false },
            onMonthSelected = {
                viewModel.selectMonth(it)
                showMonthPicker = false
            }
        )
    }

    Scaffold(
        topBar = {
            Surface(color = Background) {
                DashboardHeader(
                    userName = userName,
                    onNotificationClick = { navController.navigate(Screen.Notification.route) }
                )
            }
        },
        bottomBar = { DashboardBottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (pendingDraftsCount > 0) {
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { navController.navigate(Screen.Transaction.route) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Primary.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = Primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Ada $pendingDraftsCount transaksi butuh konfirmasi",
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            BalanceCard(balance = totalBalance)
            
            IncomeExpenseRow(income = totalIncome, expense = totalExpense)

            QuickActionCard(navController = navController)

            SummaryCard(
                transactions = monthlyTransactions,
                selectedMonth = selectedMonth,
                onMonthClick = { showMonthPicker = true }
            )

            RecentTransactionCard(
                navController = navController,
                transactions = recentTransactions
            )
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
