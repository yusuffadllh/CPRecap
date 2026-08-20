package com.yusuffdllh.smartfinance.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RecentTransactionCard(
    navController: NavController,
    transactions: List<TransactionEntity> = emptyList()
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transaksi Terakhir",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
            TextButton(
                onClick = { navController.navigate(Screen.Transaction.route) },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Lihat semua",
                    style = MaterialTheme.typography.labelLarge,
                    color = Primary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (transactions.isEmpty()) {
            Text(
                text = "Belum ada transaksi",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                transactions.take(5).forEach { transaction ->
                    TransactionCardItem(transaction)
                }
            }
        }
    }
}

@Composable
fun TransactionCardItem(transaction: TransactionEntity) {
    val isIncome = transaction.type == "INCOME"
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Border.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getCategoryIcon(transaction.category),
                    contentDescription = null,
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = formatDate(transaction.date),
                    style = MaterialTheme.typography.labelLarge,
                    color = TextSecondary
                )
            }
            
            Text(
                text = "${if (isIncome) "+" else "-"}Rp${formatCurrency(transaction.amount)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (isIncome) Primary else Danger
            )
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
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
