package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun AddTransactionHeader(

    title: String,

    onBackClick: () -> Unit

) {

    Column {

        Row(

            modifier = Modifier.fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically

        ) {

            IconButton(

                onClick = onBackClick

            ) {

                Icon(

                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,

                    contentDescription = "Back",

                    tint = TextPrimary

                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {

                Text(

                    text = title,

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold,

                    color = TextPrimary

                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(

                    text = "Catat transaksi keuanganmu",

                    style = MaterialTheme.typography.bodyMedium,

                    color = TextSecondary

                )

            }

        }

    }

}