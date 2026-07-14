package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun DashboardHeader(

    userName: String = "Yusuf"

) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically

    ) {

        Column {

            Text(

                text = "Selamat Datang 👋",

                color = TextSecondary,

                fontSize = 14.sp

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(

                text = userName,

                color = TextPrimary,

                fontWeight = FontWeight.Bold,

                fontSize = 24.sp

            )

        }

        Icon(

            imageVector = Icons.Outlined.Notifications,

            contentDescription = null,

            tint = TextPrimary

        )

    }

}