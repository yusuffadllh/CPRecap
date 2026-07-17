package com.yusuffdllh.smartfinance.screen.settings.security.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun SecurityHeader(

    onBackClick: () -> Unit

) {

    Box(

        modifier = Modifier.fillMaxWidth(),

        contentAlignment = Alignment.Center

    ) {

        IconButton(

            modifier = Modifier.align(Alignment.CenterStart),

            onClick = onBackClick

        ) {

            Icon(

                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,

                contentDescription = null,

                tint = TextPrimary

            )

        }

        Text(

            text = "Keamanan",

            style = MaterialTheme.typography.titleLarge,

            fontWeight = FontWeight.Bold,

            color = TextPrimary

        )

    }

}