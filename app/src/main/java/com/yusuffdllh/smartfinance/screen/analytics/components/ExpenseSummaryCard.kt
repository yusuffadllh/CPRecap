package com.yusuffdllh.smartfinance.screen.analytics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun ExpenseSummaryCard(

    expense: Long,

    income: Long,

    expenseGrowth: Float,

    incomeGrowth: Float

) {

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
                .padding(24.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            SummarySection(

                modifier = Modifier.weight(1f),

                title = "Pengeluaran",

                amount = expense,

                growth = expenseGrowth,

                positive = false

            )

            HorizontalDivider(

                modifier = Modifier
                    .height(110.dp)
                    .width(1.dp),

                color = Border

            )

            Spacer(modifier = Modifier.width(20.dp))

            SummarySection(

                modifier = Modifier.weight(1f),

                title = "Pemasukan",

                amount = income,

                growth = incomeGrowth,

                positive = true

            )

        }

    }

}

@Composable
private fun SummarySection(

    modifier: Modifier,

    title: String,

    amount: Long,

    growth: Float,

    positive: Boolean

) {

    Column(

        modifier = modifier

    ) {

        Text(

            text = title,

            color = TextSecondary,

            fontSize = 17.sp

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = "Rp${formatCurrency(amount)}",

            color = if (positive) Primary else Danger,

            fontSize = 22.sp,

            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(

            verticalAlignment = Alignment.CenterVertically

        ) {

            Box(

                modifier = Modifier

                    .background(

                        color =
                            if (positive)
                                Primary.copy(alpha = .15f)
                            else
                                Danger.copy(alpha = .15f),

                        shape = RoundedCornerShape(12.dp)

                    )

                    .padding(

                        horizontal = 10.dp,

                        vertical = 5.dp

                    )

            ) {

                Text(

                    text =
                        if (positive)
                            "▲ ${growth}%"
                        else
                            "▼ ${growth}%",

                    color =
                        if (positive)
                            Primary
                        else
                            Danger,

                    fontWeight = FontWeight.Bold,

                    fontSize = 13.sp

                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(

                text = "dari bulan lalu",

                color = TextSecondary,

                fontSize = 13.sp

            )

        }

    }

}

private fun formatCurrency(value: Long): String {

    return "%,d".format(value).replace(',', '.')

}