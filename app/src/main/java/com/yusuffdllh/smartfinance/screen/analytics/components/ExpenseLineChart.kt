package com.yusuffdllh.smartfinance.screen.analytics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.Border
import com.yusuffdllh.smartfinance.ui.theme.Danger
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun ExpenseLineChart(
    values: List<Float>
) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)

        ) {

            Text(

                text = "Grafik Pengeluaran",

                color = TextPrimary,

                fontSize = 20.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(20.dp))

            Canvas(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)

            ) {

                //------------------------------------------------
                // GRID
                //------------------------------------------------

                repeat(5) {

                    val y = size.height / 4 * it

                    drawLine(

                        color = Border,

                        start = Offset(0f, y),

                        end = Offset(size.width, y),

                        strokeWidth = 2f

                    )

                }

                //------------------------------------------------
                // DATA
                //------------------------------------------------

                val maxValue = values.maxOrNull() ?: 1f

                val stepX = size.width / (values.size - 1)

                val points = values.mapIndexed { index, value ->

                    Offset(

                        x = stepX * index,

                        y = size.height - ((value / maxValue) * size.height * .8f)

                    )

                }

                //------------------------------------------------
                // PATH
                //------------------------------------------------

                val path = Path()

                path.moveTo(

                    points.first().x,

                    points.first().y

                )

                for (i in 1 until points.size) {

                    path.lineTo(

                        points[i].x,

                        points[i].y

                    )

                }

                drawPath(

                    path = path,

                    color = Danger,

                    style = Stroke(

                        width = 8f,

                        cap = StrokeCap.Round

                    )

                )

                //------------------------------------------------
                // POINT
                //------------------------------------------------

                points.forEach {

                    drawCircle(

                        color = Danger,

                        radius = 8f,

                        center = it

                    )

                    drawCircle(

                        color = Surface,

                        radius = 4f,

                        center = it

                    )

                }

            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                listOf(

                    "1",

                    "5",

                    "10",

                    "15",

                    "20",

                    "25"

                ).forEach {

                    Text(

                        text = it,

                        color = TextSecondary,

                        fontSize = 12.sp

                    )

                }

            }

        }

    }

}