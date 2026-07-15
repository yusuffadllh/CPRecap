package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.data.repository.TransactionItemModel
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.components.TransactionItem
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun RecentTransactionCard(

    transactions: List<TransactionItemModel>,

    navController: NavController? = null

) {

    Column {

        Row(

            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            Text(

                text = "Transaksi Terakhir",

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold,

                color = TextPrimary

            )

            TextButton(

                onClick = {

                    navController?.navigate(

                        Screen.Transaction.route

                    )

                }

            ) {

                Text(

                    text = "Lihat Semua",

                    color = Primary

                )

            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        if (transactions.isEmpty()) {

            Text(

                text = "Belum ada transaksi",

                color = TextPrimary,

                style = MaterialTheme.typography.bodyMedium

            )

        } else {

            LazyColumn(

                modifier = Modifier.heightIn(max = 360.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp),

                userScrollEnabled = false

            ) {

                items(transactions) { transaction ->

                    TransactionItem(

                        title = transaction.title,

                        date = transaction.date,

                        amount = transaction.amount,

                        income = transaction.income,

                        icon = transaction.icon

                    )

                }

            }

        }

    }

}