package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun BottomNavigationBar(

    modifier: Modifier = Modifier,

    selected: String,

    onHomeClick: () -> Unit,

    onTransactionClick: () -> Unit,

    onAddClick: () -> Unit,

    onAnalyticsClick: () -> Unit,

    onProfileClick: () -> Unit

) {

    Card(

        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 16.dp
            ),

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                ),

            horizontalArrangement = Arrangement.SpaceEvenly,

            verticalAlignment = Alignment.CenterVertically

        ) {

            BottomItem(
                icon = Icons.Default.Home,
                title = "Home",
                selected = selected == "home",
                onClick = onHomeClick
            )

            BottomItem(
                icon = Icons.Default.ReceiptLong,
                title = "Transaksi",
                selected = selected == "transaction",
                onClick = onTransactionClick
            )

            Box(

                modifier = Modifier
                    .size(64.dp)
                    .offset(y = (-12).dp)
                    .background(
                        color = Primary,
                        shape = CircleShape
                    ),

                contentAlignment = Alignment.Center

            ) {

                IconButton(
                    onClick = onAddClick
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Transaksi",
                        tint = TextPrimary
                    )

                }

            }

            BottomItem(
                icon = Icons.Default.Analytics,
                title = "Laporan",
                selected = selected == "analytics",
                onClick = onAnalyticsClick
            )

            BottomItem(
                icon = Icons.Default.AccountCircle,
                title = "Profil",
                selected = selected == "profile",
                onClick = onProfileClick
            )

        }

    }

}