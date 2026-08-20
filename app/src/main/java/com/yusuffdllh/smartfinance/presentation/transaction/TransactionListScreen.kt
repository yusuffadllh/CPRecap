package com.yusuffdllh.smartfinance.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.components.EmptyState
import com.yusuffdllh.smartfinance.components.TransactionItem
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.presentation.dashboard.components.DashboardBottomBar
import com.yusuffdllh.smartfinance.presentation.transaction.components.FilterChipRow
import com.yusuffdllh.smartfinance.presentation.transaction.components.MonthHeader
import com.yusuffdllh.smartfinance.presentation.transaction.components.SearchBar
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.utils.DoubleBackToExit
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(
    navController: NavController,
    viewModel: TransactionListViewModel = hiltViewModel()
) {
    DoubleBackToExit()
    val transactions by viewModel.transactions.collectAsState()
    val drafts by viewModel.drafts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filterType by viewModel.filterType.collectAsState()

    val groupedTransactions = transactions.groupBy {
        formatMonth(it.date)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaksi", color = TextPrimary, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) },
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
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            SearchBar(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            FilterChipRow(
                selected = filterType,
                onSelectedChange = { viewModel.onFilterTypeChanged(it) }
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            if (transactions.isEmpty() && drafts.isEmpty()) {
                EmptyState(
                    title = "Tidak ada transaksi",
                    description = "Coba ubah pencarian atau filter"
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 110.dp)
                ) {
                    if (drafts.isNotEmpty()) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Butuh Konfirmasi",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    color = com.yusuffdllh.smartfinance.ui.theme.Primary
                                )
                                TextButton(onClick = { viewModel.confirmAllDrafts() }) {
                                    Text("Konfirmasi Semua", color = com.yusuffdllh.smartfinance.ui.theme.Primary, style = MaterialTheme.typography.labelLarge)
                                }
                            }
                        }
                        items(drafts) { draft ->
                            DraftItem(
                                draft = draft,
                                onConfirm = { viewModel.confirmDraft(draft) },
                                onDelete = { viewModel.deleteDraft(draft) }
                            )
                        }
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }

                    groupedTransactions.forEach { (month, monthTransactions) ->
                        item { MonthHeader(month = month) }
                        items(monthTransactions) { transaction ->
                            TransactionItem(
                                title = transaction.title,
                                date = formatDate(transaction.date),
                                amount = "${if (transaction.type == "INCOME") "+" else "-"}Rp${formatCurrency(transaction.amount)}",
                                income = transaction.type == "INCOME",
                                icon = getCategoryIcon(transaction.category),
                                onClick = {
                                    navController.navigate(Screen.AddTransaction.createRoute(transaction.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DraftItem(
    draft: com.yusuffdllh.smartfinance.data.local.entity.TransactionDraftEntity,
    onConfirm: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = com.yusuffdllh.smartfinance.ui.theme.Surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, com.yusuffdllh.smartfinance.ui.theme.Primary.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = draft.merchant,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = draft.reference,
                        style = MaterialTheme.typography.bodySmall,
                        color = com.yusuffdllh.smartfinance.ui.theme.TextSecondary,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "Rp${formatCurrency(draft.amount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = if (draft.type == "INCOME") com.yusuffdllh.smartfinance.ui.theme.Primary else com.yusuffdllh.smartfinance.ui.theme.Danger
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = com.yusuffdllh.smartfinance.ui.theme.Danger),
                    border = androidx.compose.foundation.BorderStroke(1.dp, com.yusuffdllh.smartfinance.ui.theme.Danger.copy(alpha = 0.5f))
                ) {
                    Text("Abaikan")
                }
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = com.yusuffdllh.smartfinance.ui.theme.Primary)
                ) {
                    Text("Konfirmasi")
                }
            }
        }
    }
}

private fun formatMonth(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMMM yyyy", Locale("id", "ID"))
    return sdf.format(Date(timestamp))
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
    return sdf.format(Date(timestamp))
}

private fun formatCurrency(value: Long): String {
    return "%,d".format(value).replace(',', '.')
}

private fun getCategoryIcon(category: String): ImageVector {
    return when (category.lowercase()) {
        "food", "makanan" -> Icons.Default.Fastfood
        "transport", "transportasi" -> Icons.Default.DirectionsBus
        "shopping", "belanja" -> Icons.Default.ShoppingBag
        else -> Icons.Default.AttachMoney
    }
}
