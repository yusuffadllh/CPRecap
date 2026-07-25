package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun BudgetProgress(
    category: String,
    spent: Long,
    total: Long,
    modifier: Modifier = Modifier
) {
    val progress = if (total > 0) spent.toFloat() / total else 0f
    val isWarning = progress > 0.9f

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = category, color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(
                text = "${(progress * 100).toInt()}%",
                color = if (isWarning) Danger else Primary,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = if (isWarning) Danger else Primary,
            trackColor = Border,
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Rp${formatCurrency(spent)} / Rp${formatCurrency(total)}",
            color = TextSecondary,
            fontSize = 12.sp
        )
    }
}

private fun formatCurrency(value: Long): String {
    return value.toString().reversed().chunked(3).joinToString(".").reversed()
}
