package com.yusuffdllh.smartfinance.presentation.analytics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun ExpenseChart(
    incomeData: List<Float>,
    expenseData: List<Float>,
    labels: List<String>
) {
    val maxVal = (maxOf(incomeData.maxOrNull() ?: 0f, expenseData.maxOrNull() ?: 0f) * 1.2f).coerceAtLeast(1000f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Surface)
            .padding(16.dp)
    ) {
        Column {
            Row(modifier = Modifier.weight(1f)) {
                // Y-Axis
                Column(
                    modifier = Modifier.fillMaxHeight().width(45.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(formatYAxis(maxVal), style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
                    Text(formatYAxis(maxVal * 0.66f), style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
                    Text(formatYAxis(maxVal * 0.33f), style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
                    Text("0", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
                }

                // Drawing Area
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height

                        // Grid lines
                        for (i in 0..3) {
                            val y = (height / 3) * i
                            drawLine(Border.copy(alpha = 0.2f), Offset(0f, y), Offset(width, y), 1.dp.toPx())
                        }

                        fun getPos(index: Int, value: Float, count: Int): Offset {
                            val x = if (count > 1) (index.toFloat() / (count - 1)) * width else width / 2
                            val y = height - (value / maxVal) * height
                            return Offset(x, y.coerceIn(0f, height))
                        }

                        // Pemasukan (Primary)
                        if (incomeData.isNotEmpty()) {
                            val path = Path()
                            incomeData.forEachIndexed { i, v ->
                                val pos = getPos(i, v, incomeData.size)
                                if (i == 0) path.moveTo(pos.x, pos.y) else path.lineTo(pos.x, pos.y)
                                drawCircle(Primary, 4.dp.toPx(), pos)
                            }
                            if (incomeData.size > 1) drawPath(path, Primary, style = Stroke(width = 2.dp.toPx()))
                        }

                        // Pengeluaran (Danger)
                        if (expenseData.isNotEmpty()) {
                            val path = Path()
                            expenseData.forEachIndexed { i, v ->
                                val pos = getPos(i, v, expenseData.size)
                                if (i == 0) path.moveTo(pos.x, pos.y) else path.lineTo(pos.x, pos.y)
                                drawCircle(Danger, 4.dp.toPx(), pos)
                            }
                            if (expenseData.size > 1) drawPath(path, Danger, style = Stroke(width = 2.dp.toPx()))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // X-Axis
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 45.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val displayLabels = if (labels.size > 5) {
                    listOf(labels.first(), labels[labels.size / 4], labels[labels.size / 2], labels[labels.size * 3 / 4], labels.last())
                } else labels

                displayLabels.forEach { label ->
                    Text(label, style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 9.sp)
                }
            }
        }
    }
}

private fun formatYAxis(value: Float): String {
    return when {
        value >= 1000000 -> "${(value / 1000000).toInt()}jt"
        value >= 1000 -> "${(value / 1000).toInt()}rb"
        else -> value.toInt().toString()
    }
}
