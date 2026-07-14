package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Arrangement


@Composable
fun BalanceCard(

    balance: String = "Rp12.450.000",

    growth: String = "12.5%"

) {

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier.weight(1f)
        ) {

            IncomeExpenseCard(

                title = "Pemasukan",

                amount = "Rp6.800.000",

                income = true

            )

        }

        Box(
            modifier = Modifier.weight(1f)
        ) {

            IncomeExpenseCard(

                title = "Pengeluaran",

                amount = "Rp3.250.000",

                income = false

            )

        }

    }

    Card(

        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        ),

        shape = RoundedCornerShape(24.dp)

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

                    "Total Saldo",

                    color = TextSecondary,

                    fontSize = 18.sp

                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(

                    balance,

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
                            .padding(horizontal = 12.dp, vertical = 8.dp)

                    ) {

                        Text(

                            "▲ $growth",

                            color = Primary,

                            fontWeight = FontWeight.Bold

                        )

                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(

                        "dari bulan lalu",

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

                        Icons.Outlined.Visibility,

                        contentDescription = null,

                        tint = Primary

                    )

                }

            }

        }

    }

}

