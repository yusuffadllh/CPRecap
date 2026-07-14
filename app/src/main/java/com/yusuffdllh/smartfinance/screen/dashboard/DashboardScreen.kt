package com.yusuffdllh.smartfinance.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.screen.dashboard.components.BalanceCard
import com.yusuffdllh.smartfinance.screen.dashboard.components.DashboardHeader
import com.yusuffdllh.smartfinance.screen.dashboard.components.IncomeExpenseCard
import com.yusuffdllh.smartfinance.screen.dashboard.components.SummaryCard
import com.yusuffdllh.smartfinance.ui.theme.Background
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.screen.dashboard.components.RecentTransactionCard
import com.yusuffdllh.smartfinance.screen.dashboard.components.BottomNavigationBar
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.navigation.Screen

@Composable
fun DashboardScreen(

    navController: NavController

) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)

    ) {

        DashboardHeader()

        Spacer(modifier = Modifier.height(24.dp))

        BalanceCard()

        Spacer(modifier = Modifier.height(16.dp))

        Row(

            horizontalArrangement = Arrangement.spacedBy(12.dp),

            modifier = Modifier.fillMaxWidth()

        ) {

            Box(
                modifier = Modifier.weight(1f)
            ) {

                IncomeExpenseCard(

                    title = "Pemasukan",

                    amount = "Rp6.800.000",

                    income = true

                )

            }

            Box(
                modifier = Modifier.weight(1f)
            ) {

                IncomeExpenseCard(

                    title = "Pengeluaran",

                    amount = "Rp3.250.000",

                    income = false

                )

            }

        }

        Text(
            text = "Ringkasan Bulan Ini",
            color = TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        SummaryCard()

        Spacer(modifier = Modifier.height(24.dp))

        RecentTransactionCard()

        Spacer(modifier = Modifier.height(24.dp))

        BottomNavigationBar(

            selected = "home",

            onHomeClick = {
                // sudah di dashboard
            },

            onTransactionClick = {

                navController.navigate(Screen.Transaction.route)

            },

            onAddClick = {

                navController.navigate(Screen.AddTransaction.route)

            },

            onAnalyticsClick = {

                navController.navigate(Screen.Analytics.route)

            },

            onProfileClick = {

                navController.navigate(Screen.Profile.route)

            }

        )

    }

}