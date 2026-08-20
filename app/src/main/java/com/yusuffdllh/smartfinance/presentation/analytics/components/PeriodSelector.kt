package com.yusuffdllh.smartfinance.presentation.analytics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun PeriodSelector(
    selectedPeriod: String,
    onPeriodSelected: (String) -> Unit
) {
    val periods = listOf("Mingguan", "Bulanan", "Tahunan")
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Surface)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        periods.forEach { period ->
            val isSelected = selectedPeriod == period
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isSelected) Primary else Transparent)
                    .clickable { onPeriodSelected(period) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = period,
                    color = if (isSelected) TextPrimary else TextSecondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
