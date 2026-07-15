package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun IncomeExpenseCard(

    title: String,

    amount: Long,

    income: Boolean

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Column(

            modifier = Modifier.padding(18.dp)

        ) {

            Box(

                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (income)
                            Primary.copy(alpha = .15f)
                        else
                            Danger.copy(alpha = .15f)
                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = if (income)
                        Icons.Default.ArrowDownward
                    else
                        Icons.Default.ArrowUpward,

                    contentDescription = null,

                    tint = if (income)
                        Primary
                    else
                        Danger

                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(

                text = title,

                color = TextSecondary,

                style = MaterialTheme.typography.bodyMedium

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = "Rp${formatCurrency(amount)}",

                color = TextPrimary,

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold

            )

        }

    }

}

private fun formatCurrency(

    value: Long

): String {

    return value
        .toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()

}