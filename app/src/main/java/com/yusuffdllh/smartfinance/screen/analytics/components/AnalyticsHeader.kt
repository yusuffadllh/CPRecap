package com.yusuffdllh.smartfinance.screen.analytics.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun AnalyticsHeader(

    navController: NavController

) {

    Row(

        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically

    ) {

        //----------------------------------
        // Back Button
        //----------------------------------

        IconButton(

            onClick = {

                navController.popBackStack()

            }

        ) {

            Icon(

                imageVector = Icons.AutoMirrored.Filled.ArrowBack,

                contentDescription = "Back",

                tint = TextPrimary

            )

        }

        Spacer(modifier = Modifier.width(8.dp))

        //----------------------------------
        // Title
        //----------------------------------

        Column {

            Text(

                text = "Laporan",

                color = TextPrimary,

                fontSize = 28.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(

                text = "Pantau pemasukan dan pengeluaran",

                color = TextSecondary,

                fontSize = 14.sp

            )

        }

    }

}