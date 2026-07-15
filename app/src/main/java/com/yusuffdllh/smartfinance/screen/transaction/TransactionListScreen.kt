package com.yusuffdllh.smartfinance.screen.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.ShoppingBag
import com.yusuffdllh.smartfinance.components.EmptyState
import com.yusuffdllh.smartfinance.components.TransactionItem
import com.yusuffdllh.smartfinance.data.model.TransactionType
import com.yusuffdllh.smartfinance.data.repository.DummyRepository
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.screen.dashboard.components.BottomNavigationBar
import com.yusuffdllh.smartfinance.screen.transaction.components.FilterChipRow
import com.yusuffdllh.smartfinance.screen.transaction.components.MonthHeader
import com.yusuffdllh.smartfinance.screen.transaction.components.SearchBar
import com.yusuffdllh.smartfinance.screen.transaction.components.TransactionHeader
import com.yusuffdllh.smartfinance.utils.formatCurrency

@Composable
fun TransactionListScreen(

    navController: NavController

) {

    var search by remember {

        mutableStateOf("")

    }

    var filter by remember {

        mutableStateOf("Semua")

    }

    val filteredTransactions = DummyRepository.transactions.filter { transaction ->

        val searchMatch = transaction.title.contains(

            search,

            ignoreCase = true

        )

        val filterMatch = when (filter) {

            "Pemasukan" ->
                transaction.type == TransactionType.INCOME

            "Pengeluaran" ->
                transaction.type == TransactionType.EXPENSE

            else ->
                true

        }

        searchMatch && filterMatch

    }

    val groupedTransactions =

        filteredTransactions.groupBy {

            monthFromDate(it.date)

        }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {

        TransactionHeader(

            onBackClick = {

                navController.popBackStack()

            }

        )

        Spacer(modifier = Modifier.height(20.dp))

        SearchBar(

            value = search,

            onValueChange = {

                search = it

            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        FilterChipRow(

            selected = filter,

            onSelectedChange = {

                filter = it

            }

        )

        Spacer(modifier = Modifier.height(24.dp))

        if (filteredTransactions.isEmpty()) {

            EmptyState(

                title = "Tidak ada transaksi",

                description = "Coba ubah pencarian atau filter"

            )

            Spacer(modifier = Modifier.weight(1f))

        } else {

            LazyColumn(

                modifier = Modifier.weight(1f),

                verticalArrangement = Arrangement.spacedBy(14.dp)

            ) {

                groupedTransactions.forEach { (month, transactions) ->

                    item {

                        MonthHeader(

                            month = month

                        )

                    }

                    items(transactions) { transaction ->

                        TransactionItem(

                            title = transaction.title,

                            date = transaction.date,

                            amount = formatCurrency(transaction.amount),

                            income = transaction.type == TransactionType.INCOME,

                            icon = transactionIcon(transaction.category)

                        )

                    }

                }

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        BottomNavigationBar(

            selected = "transaction",

            onHomeClick = {

                navController.navigate(Screen.Dashboard.route)

            },

            onTransactionClick = {

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

private fun monthFromDate(

    date: String

): String {

    return date.split(" ").drop(1).joinToString(" ")

}

private fun transactionIcon(

    category: String

): ImageVector {

    return when (category.lowercase()) {

        "food",
        "makanan" -> Icons.Default.Fastfood

        "transport",
        "transportasi" -> Icons.Default.DirectionsBus

        "shopping",
        "belanja" -> Icons.Default.ShoppingBag

        "salary",
        "gaji" -> Icons.Default.AttachMoney

        else -> Icons.Default.AttachMoney

    }

}