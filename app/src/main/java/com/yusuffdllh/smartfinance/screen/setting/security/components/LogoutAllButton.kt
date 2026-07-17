package com.yusuffdllh.smartfinance.screen.settings.security.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Danger
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun LogoutAllButton(

    onClick: () -> Unit

) {

    Button(

        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),

        shape = RoundedCornerShape(16.dp),

        colors = ButtonDefaults.buttonColors(

            containerColor = Danger

        )

    ) {

        Text(

            text = "Keluar dari Semua Perangkat",

            style = MaterialTheme.typography.labelLarge,

            fontWeight = FontWeight.Bold,

            color = TextPrimary

        )

    }

}