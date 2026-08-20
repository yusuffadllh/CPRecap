package com.yusuffdllh.smartfinance.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.components.MonthPickerBottomSheet
import com.yusuffdllh.smartfinance.presentation.analytics.components.*
import com.yusuffdllh.smartfinance.presentation.dashboard.DashboardViewModel
import com.yusuffdllh.smartfinance.presentation.dashboard.components.DashboardBottomBar
import com.yusuffdllh.smartfinance.presentation.transaction.components.DatePickerDialog
import com.yusuffdllh.smartfinance.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    navController: NavController,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val categoryBreakdown by viewModel.categoryBreakdown.collectAsState()
    val selectedPeriod by viewModel.selectedPeriod.collectAsState()
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    val trendData by viewModel.monthlyTrend.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }
    var showMonthPicker by remember { mutableStateOf(false) }

    val totalExpense = categoryBreakdown.values.sum()

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = {
                viewModel.selectDate(it)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

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
            TopAppBar(
                title = { 
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Laporan", color = TextPrimary, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
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
            // Period Selector
            PeriodSelector(
                selectedPeriod = selectedPeriod,
                onPeriodSelected = { viewModel.selectPeriod(it) }
            )

            // Month Selector Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showMonthPicker = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = selectedMonth, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = TextPrimary)
            }

            // Balance Summary Row
            AnalyticsBalanceRow(
                income = trendData.sumOf { it.second },
                expense = totalExpense
            )

            // Total Expense Card
            MonthlyExpenseCard(amount = totalExpense)

            // Line Chart
            val incomePoints = trendData.map { it.second.toFloat() }
            val expensePoints = trendData.map { it.third.toFloat() }
            val labels = trendData.map { SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(it.first)) }
            
            ExpenseChart(
                incomeData = if (incomePoints.isEmpty()) listOf(0f, 0f) else incomePoints,
                expenseData = if (expensePoints.isEmpty()) listOf(0f, 0f) else expensePoints,
                labels = if (labels.isEmpty()) listOf("", "", "", "", "") else labels
            )

            // Category Breakdown
            CategorySummaryCard(categoryBreakdown = categoryBreakdown)
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
