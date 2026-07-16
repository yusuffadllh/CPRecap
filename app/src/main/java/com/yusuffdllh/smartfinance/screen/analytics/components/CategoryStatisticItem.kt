package com.yusuffdllh.smartfinance.screen.analytics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun CategoryStatisticItem(

    title: String,

    amount: Long,

    percentage: Int,

    color: Color,

    icon: String

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),

        verticalAlignment = Alignment.CenterVertically

    ) {

        //----------------------------------
        // Icon
        //----------------------------------

        Box(

            modifier = Modifier
                .size(52.dp)
                .background(

                    color.copy(alpha = .18f),

                    RoundedCornerShape(16.dp)

                ),

            contentAlignment = Alignment.Center

        ) {

            Text(

                text = icon,

                fontSize = 24.sp

            )

        }

        Spacer(modifier = Modifier.width(16.dp))

        //----------------------------------
        // Content
        //----------------------------------

        Column(

            modifier = Modifier.weight(1f)

        ) {

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text(

                    text = title,

                    color = TextPrimary,

                    fontWeight = FontWeight.SemiBold,

                    fontSize = 16.sp

                )

                Text(

                    text = "$percentage%",

                    color = color,

                    fontWeight = FontWeight.Bold,

                    fontSize = 15.sp

                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(

                progress = { percentage / 100f },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),

                color = color,

                trackColor = Border,

                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(

                text = "Rp${formatCurrency(amount)}",

                color = TextSecondary,

                fontSize = 13.sp

            )

        }

        Spacer(modifier = Modifier.width(12.dp))

        Icon(

            imageVector = Icons.Outlined.ChevronRight,

            contentDescription = null,

            tint = TextSecondary

        )

    }

}

private fun formatCurrency(value: Long): String {

    return "%,d".format(value).replace(',', '.')

}