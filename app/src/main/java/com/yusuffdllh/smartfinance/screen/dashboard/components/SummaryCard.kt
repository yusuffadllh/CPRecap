package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.data.repository.TransactionItemModel
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun SummaryCard(

    transactions: List<TransactionItemModel>

) {

    val expenseList = transactions.filter {

        !it.income

    }

    val totalExpense = expenseList.sumOf {

        it.amount
            .replace("-Rp", "")
            .replace(".", "")
            .toLong()

    }

    val food = expenseList
        .filter {

            it.category == "Makanan"

        }
        .sumOf {

            it.amount
                .replace("-Rp", "")
                .replace(".", "")
                .toLong()

        }

    val transport = expenseList
        .filter {

            it.category == "Transportasi"

        }
        .sumOf {

            it.amount
                .replace("-Rp", "")
                .replace(".", "")
                .toLong()

        }

    val shopping = expenseList
        .filter {

            it.category == "Belanja"

        }
        .sumOf {

            it.amount
                .replace("-Rp", "")
                .replace(".", "")
                .toLong()

        }

    val other = totalExpense - food - transport - shopping

    val chartData = listOf(

        food to ChartGreen,

        transport to ChartBlue,

        shopping to ChartPurple,

        other to ChartOrange

    )

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            DonutChart(

                values = chartData,

                modifier = Modifier.size(150.dp)

            )

            Spacer(modifier = Modifier.width(24.dp))

            Column(

                modifier = Modifier.weight(1f),

                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                LegendItem(

                    "Makanan",

                    ChartGreen

                )

                LegendItem(

                    "Transportasi",

                    ChartBlue

                )

                LegendItem(

                    "Belanja",

                    ChartPurple

                )

                LegendItem(

                    "Lainnya",

                    ChartOrange

                )

            }

        }

    }

}

@Composable
private fun DonutChart(

    values: List<Pair<Long, androidx.compose.ui.graphics.Color>>,

    modifier: Modifier = Modifier

) {

    val total = values.sumOf {

        it.first

    }.coerceAtLeast(1)

    Canvas(

        modifier = modifier

    ) {

        var startAngle = -90f

        values.forEach { (value, color) ->

            val sweep = value.toFloat() / total * 360f

            drawArc(

                color = color,

                startAngle = startAngle,

                sweepAngle = sweep - 2f,

                useCenter = false,

                topLeft = Offset.Zero,

                size = Size(size.width, size.height),

                style = Stroke(

                    width = 26.dp.toPx(),

                    cap = StrokeCap.Round

                )

            )

            startAngle += sweep

        }

    }

}

@Composable
private fun LegendItem(

    title: String,

    color: androidx.compose.ui.graphics.Color

) {

    Row(

        verticalAlignment = Alignment.CenterVertically

    ) {

        Spacer(

            modifier = Modifier
                .size(12.dp)
                .padding(end = 4.dp)

        )

        Canvas(

            modifier = Modifier.size(12.dp)

        ) {

            drawCircle(

                color = color

            )

        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(

            text = title,

            color = TextPrimary,

            style = MaterialTheme.typography.bodyMedium,

            fontWeight = FontWeight.Medium

        )

    }

}