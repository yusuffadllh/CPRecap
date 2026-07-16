package com.yusuffdllh.smartfinance.screen.budget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.data.model.Budget
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun BudgetItem(

    budget: Budget

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)

        ) {

            //------------------------------------
            // Header
            //------------------------------------

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                Box(

                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(budget.color.copy(alpha = 0.15f)),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = budget.icon,

                        contentDescription = budget.category,

                        tint = budget.color

                    )

                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(

                    modifier = Modifier.weight(1f)

                ) {

                    Text(

                        text = budget.category,

                        color = TextPrimary,

                        fontWeight = FontWeight.Bold,

                        fontSize = 17.sp

                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(

                        text = "Rp${formatCurrency(budget.spent)} / Rp${formatCurrency(budget.budget)}",

                        color = TextSecondary,

                        fontSize = 13.sp

                    )

                }

                Text(

                    text = "${budget.percentage}%",

                    color = budget.color,

                    fontWeight = FontWeight.Bold,

                    fontSize = 16.sp

                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            //------------------------------------
            // Progress
            //------------------------------------

            LinearProgressIndicator(

                progress = { budget.percentage / 100f },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),

                color = budget.color,

                trackColor = Border,

                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap

            )

        }

    }

}

private fun formatCurrency(

    value: Long

): String {

    return "%,d".format(value).replace(',', '.')

}