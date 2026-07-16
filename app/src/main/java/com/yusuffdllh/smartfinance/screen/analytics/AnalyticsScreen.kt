package com.yusuffdllh.smartfinance.screen.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.screen.analytics.components.AnalyticsHeader
import com.yusuffdllh.smartfinance.screen.analytics.components.CategoryStatisticCard
import com.yusuffdllh.smartfinance.screen.analytics.components.ExpenseLineChart
import com.yusuffdllh.smartfinance.screen.analytics.components.ExpenseSummaryCard
import com.yusuffdllh.smartfinance.screen.analytics.components.MonthSelector
import com.yusuffdllh.smartfinance.screen.analytics.components.PeriodSelector
import com.yusuffdllh.smartfinance.screen.dashboard.components.BottomNavigationBar
import com.yusuffdllh.smartfinance.ui.theme.Background

@Composable
fun AnalyticsScreen(

    navController: NavController

) {

    //----------------------------------------------------
    // State
    //----------------------------------------------------

    var selectedPeriod by remember {

        mutableStateOf("monthly")

    }

    var selectedMonth by remember {

        mutableStateOf("Juli 2026")

    }

    //----------------------------------------------------
// Data
//----------------------------------------------------

    val transactions = com.yusuffdllh.smartfinance.data.repository.DummyRepository.transactions

    val income = transactions
        .filter {

            it.income

        }
        .sumOf {

            it.amount
                .replace("+Rp", "")
                .replace(".", "")
                .toLong()

        }

    val expense = transactions
        .filter {

            !it.income

        }
        .sumOf {

            it.amount
                .replace("-Rp", "")
                .replace(".", "")
                .toLong()

        }

    //----------------------------------------------------
    // Screen
    //----------------------------------------------------

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)

    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 110.dp)

        ) {

            //----------------------------------------------------
            // Header
            //----------------------------------------------------

            AnalyticsHeader(

                navController = navController

            )

            Spacer(modifier = Modifier.height(24.dp))

            //----------------------------------------------------
            // Period
            //----------------------------------------------------

            PeriodSelector(

                selected = selectedPeriod,

                onWeeklyClick = {

                    selectedPeriod = "weekly"

                },

                onMonthlyClick = {

                    selectedPeriod = "monthly"

                },

                onYearlyClick = {

                    selectedPeriod = "yearly"

                }

            )

            Spacer(modifier = Modifier.height(18.dp))

            //----------------------------------------------------
            // Month
            //----------------------------------------------------

            MonthSelector(

                month = selectedMonth,

                onClick = {

                    // nanti buka month picker

                }

            )

            Spacer(modifier = Modifier.height(24.dp))

            //----------------------------------------------------
            // Summary
            //----------------------------------------------------

            ExpenseSummaryCard(

                expense = expense,

                income = income,

                expenseGrowth = 12.5f,

                incomeGrowth = 8.3f

            )

            Spacer(modifier = Modifier.height(24.dp))

            //----------------------------------------------------
            // Line Chart
            //----------------------------------------------------

            val chartData = listOf(

                450000f,

                300000f,

                620000f,

                500000f,

                780000f,

                expense.toFloat()

            )

            ExpenseLineChart(
                values = chartData
            )

            Spacer(modifier = Modifier.height(24.dp))

            //----------------------------------------------------
            // Category Statistic
            //----------------------------------------------------

            CategoryStatisticCard(
                transactions = transactions
            )

            Spacer(modifier = Modifier.height(24.dp))

        }

        //----------------------------------------------------
        // Bottom Navigation
        //----------------------------------------------------

        BottomNavigationBar(

            modifier = Modifier.align(androidx.compose.ui.Alignment.BottomCenter),

            selected = "analytics",

            onHomeClick = {

                navController.navigate(Screen.Dashboard.route)

            },

            onTransactionClick = {

                navController.navigate(Screen.Transaction.route)

            },

            onAddClick = {

                navController.navigate(Screen.AddTransaction.route)

            },

            onAnalyticsClick = {

            },

            onProfileClick = {

                navController.navigate(Screen.Profile.route)

            }

        )

    }

}