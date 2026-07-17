package com.yusuffdllh.smartfinance.screen.settings.account.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.IconPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun AccountHeader(

    onBackClick: () -> Unit

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.Start

    ) {

        IconButton(

            onClick = onBackClick

        ) {

            Icon(

                imageVector = Icons.AutoMirrored.Filled.ArrowBack,

                contentDescription = null,

                tint = IconPrimary

            )

        }

        Text(

            text = "Pengaturan Akun",

            style = MaterialTheme.typography.titleLarge,

            fontWeight = FontWeight.Bold,

            color = TextPrimary

        )

    }

}