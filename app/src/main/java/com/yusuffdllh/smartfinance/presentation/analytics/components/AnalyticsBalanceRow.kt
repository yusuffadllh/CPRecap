package com.yusuffdllh.smartfinance.presentation.analytics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun AnalyticsBalanceRow(
    income: Long,
    expense: Long
) {
    val balance = income - expense
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Ringkasan Saldo", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Rp${formatCurrency(balance)}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (balance >= 0) Primary else Danger,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(100.dp)).background(Primary))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pemasukan", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                }
                Text("Rp${formatCurrency(income)}", style = MaterialTheme.typography.labelLarge, color = TextPrimary)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(100.dp)).background(Danger))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pengeluaran", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                }
                Text("Rp${formatCurrency(expense)}", style = MaterialTheme.typography.labelLarge, color = TextPrimary)
            }
        }
    }
}

private fun formatCurrency(value: Long): String {
    return "%,d".format(value).replace(',', '.')
}
