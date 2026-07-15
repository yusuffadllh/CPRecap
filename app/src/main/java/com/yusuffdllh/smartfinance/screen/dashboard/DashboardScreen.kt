package com.yusuffdllh.smartfinance.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.data.repository.DummyRepository
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.screen.dashboard.components.*
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun DashboardScreen(

    navController: NavController

) {

    val transactions = DummyRepository.transactions

    val incomeTotal = transactions
        .filter { it.income }
        .sumOf {

            it.amount
                .replace("+Rp", "")
                .replace(".", "")
                .toLong()

        }

    val expenseTotal = transactions
        .filter { !it.income }
        .sumOf {

            it.amount
                .replace("-Rp", "")
                .replace(".", "")
                .toLong()

        }

    val balance = incomeTotal - expenseTotal

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
                .padding(top = 20.dp, bottom = 100.dp)

        ) {

            DashboardHeader()

            Spacer(modifier = Modifier.height(20.dp))

            BalanceCard(

                balance = balance

            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                Box(

                    modifier = Modifier.weight(1f)

                ) {

                    IncomeExpenseCard(

                        title = "Pemasukan",

                        amount = incomeTotal,

                        income = true

                    )

                }

                Box(

                    modifier = Modifier.weight(1f)

                ) {

                    IncomeExpenseCard(

                        title = "Pengeluaran",

                        amount = expenseTotal,

                        income = false

                    )

                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(

                text = "Ringkasan Bulan Ini",

                color = TextPrimary,

                fontSize = 18.sp,

                fontWeight = FontWeight.SemiBold

            )

            Spacer(modifier = Modifier.height(16.dp))

            SummaryCard(

                transactions = transactions

            )

            Spacer(modifier = Modifier.height(24.dp))

            RecentTransactionCard(

                transactions = transactions.take(5),

                navController = navController

            )

        }

        BottomNavigationBar(

            selected = "home",

            onHomeClick = {

            },

            onTransactionClick = {

                navController.navigate(Screen.Transaction.route) {

                    popUpTo(navController.graph.startDestinationId) {

                        saveState = true

                    }

                    launchSingleTop = true
                    restoreState = true

                }

            },

            onAddClick = {

                navController.navigate(Screen.AddTransaction.route) {

                    popUpTo(navController.graph.startDestinationId) {

                        saveState = true

                    }

                    launchSingleTop = true
                    restoreState = true

                }

            },

            onAnalyticsClick = {

                navController.navigate(Screen.Analytics.route) {

                    popUpTo(navController.graph.startDestinationId) {

                        saveState = true

                    }

                    launchSingleTop = true
                    restoreState = true

                }

            },

            onProfileClick = {

                navController.navigate(Screen.Profile.route) {

                    popUpTo(navController.graph.startDestinationId) {

                        saveState = true

                    }

                    launchSingleTop = true
                    restoreState = true

                }

            }

        )

    }

}