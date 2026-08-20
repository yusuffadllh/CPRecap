package com.yusuffdllh.smartfinance.presentation.analytics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun CategorySummaryCard(
    categoryBreakdown: Map<String, Long>
) {
    // Only real expense categories (amount > 0), sorted by nominal descending.
    val entries = categoryBreakdown
        .filterValues { it > 0L }
        .entries
        .sortedByDescending { it.value }
    val totalExpense = entries.sumOf { it.value }.coerceAtLeast(1L)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Pengeluaran per Kategori",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            if (entries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada pengeluaran pada periode ini",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            } else {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    entries.forEach { entry ->
                        val categoryName = entry.key
                        val amount = entry.value
                        val percentage = (amount.toFloat() / totalExpense * 100).toInt()
                        CategoryItem(
                            name = categoryName,
                            amount = amount,
                            percentage = percentage,
                            icon = getCategoryIcon(categoryName),
                            color = getCategoryColor(categoryName)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    name: String,
    amount: Long,
    percentage: Int,
    icon: ImageVector,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = name, tint = color, modifier = Modifier.size(20.dp))
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = name, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                Text(text = "Rp${formatCurrency(amount)}", style = MaterialTheme.typography.labelLarge, color = TextPrimary, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                    progress = { percentage / 100f },
                    modifier = Modifier.weight(1f).height(6.dp),
                    color = color,
                    trackColor = Border,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "$percentage%", style = MaterialTheme.typography.labelLarge, color = TextSecondary)
            }
        }
    }
}

private fun formatCurrency(value: Long): String {
    return "%,d".format(value).replace(',', '.')
}

private fun getCategoryIcon(category: String): ImageVector {
    return when (category.lowercase()) {
        "makanan", "makan", "food" -> Icons.Default.Fastfood
        "transportasi", "transport" -> Icons.Default.DirectionsBus
        "belanja", "shopping" -> Icons.Default.ShoppingBag
        "hiburan", "entertainment" -> Icons.Default.Movie
        "tagihan", "bills" -> Icons.Default.Receipt
        "kesehatan", "health" -> Icons.Default.Favorite
        else -> Icons.Default.AttachMoney
    }
}

private fun getCategoryColor(category: String): Color {
    return when (category.lowercase()) {
        "makanan", "makan", "food" -> Primary
        "transportasi", "transport" -> Secondary
        "belanja", "shopping" -> Color(0xFF8B5CF6)
        "tagihan", "bills" -> Warning
        "hiburan", "entertainment" -> Danger
        "kesehatan", "health" -> Color(0xFFEC4899)
        else -> Color(0xFF38BDF8)
    }
}
