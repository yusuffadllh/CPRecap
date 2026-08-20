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
fun MonthlyExpenseCard(
    amount: Long,
    growth: String = "23%"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Pengeluaran",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Rp${formatCurrency(amount)}",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 32.sp),
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(Danger.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "▼$growth",
                        color = Danger,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "dari bulan lalu",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

private fun formatCurrency(value: Long): String {
    return "%,d".format(value).replace(',', '.')
}
