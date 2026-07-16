package com.yusuffdllh.smartfinance.screen.budget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun BudgetSummaryCard(

    totalBudget: Long,

    totalSpent: Long,

    percentage: Int

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

            //------------------------------------------------
            // Header
            //------------------------------------------------

            Text(

                text = "Ringkasan Budget",

                color = TextPrimary,

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(20.dp))

            //------------------------------------------------
            // Budget
            //------------------------------------------------

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Column {

                    Text(

                        text = "Total Budget",

                        color = TextSecondary,

                        fontSize = 14.sp

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(

                        text = "Rp${formatCurrency(totalBudget)}",

                        color = Primary,

                        fontSize = 22.sp,

                        fontWeight = FontWeight.Bold

                    )

                }

                Column(

                    horizontalAlignment = Alignment.End

                ) {

                    Text(

                        text = "Terpakai",

                        color = TextSecondary,

                        fontSize = 14.sp

                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(

                        text = "Rp${formatCurrency(totalSpent)}",

                        color = Danger,

                        fontSize = 22.sp,

                        fontWeight = FontWeight.Bold

                    )

                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            //------------------------------------------------
            // Progress
            //------------------------------------------------

            LinearProgressIndicator(

                progress = { percentage / 100f },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),

                color = Primary,

                trackColor = Border,

                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap

            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(

                text = "$percentage% dari budget telah digunakan",

                color = TextSecondary,

                fontSize = 14.sp

            )

        }

    }

}

private fun formatCurrency(

    value: Long

): String {

    return "%,d".format(value).replace(',', '.')

}