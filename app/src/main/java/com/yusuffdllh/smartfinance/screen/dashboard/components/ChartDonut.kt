package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun ChartDonut() {

    val values = listOf(35f, 20f, 18f, 15f, 12f)

    val colors = listOf(
        Color(0xFF22C55E),
        Color(0xFF3B82F6),
        Color(0xFF8B5CF6),
        Color(0xFFF59E0B),
        Color(0xFFEF4444)
    )

    // Jarak antar segmen (dalam derajat)
    val gapAngle = 3f

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        var startAngle = -90f

        values.forEachIndexed { index, value ->

            val sweepAngle = value * 3.6f

            drawArc(
                color = colors[index],
                startAngle = startAngle + gapAngle / 2,
                sweepAngle = sweepAngle - gapAngle,
                useCenter = false,
                size = Size(size.width, size.height),
                style = Stroke(
                    width = 34f,
                    cap = StrokeCap.Round
                )
            )

            startAngle += sweepAngle

        }

    }

}