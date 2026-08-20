package com.yusuffdllh.smartfinance.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
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
fun IncomeExpenseRow(
    income: Long = 0,
    expense: Long = 0
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IncomeExpenseItem(
            title = "Pemasukan",
            amount = income,
            isIncome = true,
            modifier = Modifier.weight(1f)
        )
        IncomeExpenseItem(
            title = "Pengeluaran",
            amount = expense,
            isIncome = false,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun IncomeExpenseItem(
    title: String,
    amount: Long,
    isIncome: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isIncome) Primary.copy(alpha = 0.15f)
                        else Danger.copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isIncome) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                    contentDescription = null,
                    tint = if (isIncome) Primary else Danger,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rp${formatCurrency(amount)}",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
        }
    }
}

private fun formatCurrency(value: Long): String {
    return "%,d".format(value).replace(',', '.')
}
