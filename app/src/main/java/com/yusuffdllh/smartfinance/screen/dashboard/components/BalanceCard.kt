package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun BalanceCard(

    balance: Long,

    growth: String = "12.5%"

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

            horizontalArrangement = Arrangement.SpaceBetween,

            verticalAlignment = Alignment.CenterVertically

        ) {

            Column(

                modifier = Modifier.weight(1f)

            ) {

                Text(

                    text = "Total Saldo",

                    color = TextSecondary,

                    fontSize = 18.sp

                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(

                    text = "Rp${formatCurrency(balance)}",

                    color = TextPrimary,

                    fontWeight = FontWeight.Bold,

                    fontSize = 34.sp

                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Box(

                        modifier = Modifier
                            .background(
                                Primary.copy(alpha = .2f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            )

                    ) {

                        Text(

                            text = "▲ $growth",

                            color = Primary,

                            fontWeight = FontWeight.Bold

                        )

                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(

                        text = "dari bulan lalu",

                        color = TextSecondary

                    )

                }

            }

            Card(

                colors = CardDefaults.cardColors(

                    containerColor = Primary.copy(alpha = .18f)

                )

            ) {

                IconButton(

                    onClick = { }

                ) {

                    Icon(

                        imageVector = Icons.Outlined.Visibility,

                        contentDescription = null,

                        tint = Primary

                    )

                }

            }

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