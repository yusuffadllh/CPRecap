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

@Composable
fun DashboardScreen() {

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

        Spacer(modifier = Modifier.height(24.dp))

        SummaryCard()

        Spacer(modifier = Modifier.height(40.dp))

    }

}