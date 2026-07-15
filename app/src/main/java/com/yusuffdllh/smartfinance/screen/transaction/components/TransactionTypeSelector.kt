package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun TransactionTypeSelector(

    selectedIncome: Boolean,

    onTypeChanged: (Boolean) -> Unit

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Surface),

        verticalAlignment = Alignment.CenterVertically

    ) {

        Box(

            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(4.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (!selectedIncome) Danger else Surface
                )
                .clickable {

                    onTypeChanged(false)

                },

            contentAlignment = Alignment.Center

        ) {

            Text(

                text = "Pengeluaran",

                color = TextPrimary,

                fontSize = 16.sp,

                fontWeight = FontWeight.Medium

            )

        }

        Box(

            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(4.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (selectedIncome) Primary else Surface
                )
                .clickable {

                    onTypeChanged(true)

                },

            contentAlignment = Alignment.Center

        ) {

            Text(

                text = "Pemasukan",

                color = TextPrimary,

                fontSize = 16.sp,

                fontWeight = FontWeight.Medium

            )

        }

    }

}