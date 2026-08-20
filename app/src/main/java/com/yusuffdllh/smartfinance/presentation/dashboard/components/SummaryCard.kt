package com.yusuffdllh.smartfinance.presentation.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun SummaryCard(
    transactions: List<TransactionEntity>,
    selectedMonth: String,
    onMonthClick: () -> Unit
) {
    val expenseList = transactions.filter { it.type == "EXPENSE" }
    val totalExpense = expenseList.sumOf { it.amount }
    val totalIncome = transactions.filter { it.type == "INCOME" }.sumOf { it.amount }

    val food = expenseList.filter { it.category == "Makanan" }.sumOf { it.amount }
    val transport = expenseList.filter { it.category == "Transportasi" }.sumOf { it.amount }
    val shopping = expenseList.filter { it.category == "Belanja" }.sumOf { it.amount }
    val bills = expenseList.filter { it.category == "Tagihan" }.sumOf { it.amount }
    val entertainment = expenseList.filter { it.category == "Hiburan" }.sumOf { it.amount }
    val others = totalExpense - food - transport - shopping - bills - entertainment

    val expenseChartData = listOf(
        CategoryData("Makanan", food, Primary, if(totalExpense > 0) (food.toFloat() / totalExpense * 100).toInt() else 0),
        CategoryData("Transportasi", transport, Secondary, if(totalExpense > 0) (transport.toFloat() / totalExpense * 100).toInt() else 0),
        CategoryData("Belanja", shopping, Color(0xFF8B5CF6), if(totalExpense > 0) (shopping.toFloat() / totalExpense * 100).toInt() else 0),
        CategoryData("Tagihan", bills, Warning, if(totalExpense > 0) (bills.toFloat() / totalExpense * 100).toInt() else 0),
        CategoryData("Hiburan", entertainment, Danger, if(totalExpense > 0) (entertainment.toFloat() / totalExpense * 100).toInt() else 0),
        CategoryData("Lainnya", others, TextSecondary, if(totalExpense > 0) (others.toFloat() / totalExpense * 100).toInt() else 0)
    ).filter { it.amount > 0 }

    val comparisonData = listOf(
        CategoryData("Pemasukan", totalIncome, Primary, if(totalIncome + totalExpense > 0) (totalIncome.toFloat() / (totalIncome + totalExpense) * 100).toInt() else 0),
        CategoryData("Pengeluaran", totalExpense, Danger, if(totalIncome + totalExpense > 0) (totalExpense.toFloat() / (totalIncome + totalExpense) * 100).toInt() else 0)
    ).filter { it.amount > 0 }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ringkasan Bulan Ini",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
            Surface(
                color = Surface,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable { onMonthClick() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = selectedMonth, style = MaterialTheme.typography.labelLarge, color = TextPrimary)
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            if (transactions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada transaksi bulan ini",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val displayData = if (totalExpense > 0) expenseChartData else comparisonData
                    val centerTotal = if (totalExpense > 0) totalExpense else totalIncome
                    val centerLabel = if (totalExpense > 0) "Pengeluaran" else "Pemasukan"

                    Box(modifier = Modifier.size(140.dp), contentAlignment = Alignment.Center) {
                        DonutChart(data = displayData, modifier = Modifier.size(120.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = centerLabel, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                            Text(
                                text = "Rp${formatCurrency(centerTotal)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f).padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        displayData.take(5).forEach { data ->
                            LegendItem(data)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DonutChart(data: List<CategoryData>, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        if (data.isEmpty()) return@Canvas
        var startAngle = -90f
        data.forEach { category ->
            val sweepAngle = (category.percentage.toFloat() / 100f) * 360f
            if (sweepAngle > 0) {
                drawArc(
                    color = category.color,
                    startAngle = startAngle + 1f,
                    sweepAngle = (sweepAngle - 2f).coerceAtLeast(0.1f),
                    useCenter = false,
                    style = Stroke(width = 16.dp.toPx(), cap = StrokeCap.Round),
                    size = Size(size.width, size.height)
                )
            }
            startAngle += sweepAngle
        }
    }
}

@Composable
private fun LegendItem(data: CategoryData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(data.color))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.name, style = MaterialTheme.typography.labelLarge, color = TextSecondary, maxLines = 1)
        }
        Text(text = "${data.percentage}%", style = MaterialTheme.typography.labelLarge, color = TextPrimary, fontWeight = FontWeight.SemiBold)
    }
}

data class CategoryData(val name: String, val amount: Long = 0, val color: Color, val percentage: Int)

private fun formatCurrency(value: Long): String {
    return "%,d".format(value).replace(',', '.')
}
