package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun IncomeExpenseCard(

    title: String,

    amount: String,

    income: Boolean

) {

    val icon =
        if (income)
            Icons.Default.ArrowDownward
        else
            Icons.Default.ArrowUpward

    val color =
        if (income)
            Primary
        else
            Danger

    Card(

        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        ),

        shape = RoundedCornerShape(20.dp)

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Box(

                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color.copy(alpha = .18f),
                        CircleShape
                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = icon,

                    contentDescription = null,

                    tint = color

                )

            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(

                    text = title,

                    color = TextSecondary,

                    style = MaterialTheme.typography.bodyMedium

                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(

                    text = amount,

                    color = TextPrimary,

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold

                )

            }

        }

    }

}