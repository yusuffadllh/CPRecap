package com.yusuffdllh.smartfinance.screen.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun ProfileHeader() {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        contentAlignment = Alignment.Center

    ) {

        Text(

            text = "Profil",

            color = TextPrimary,

            fontSize = 30.sp,

            fontWeight = FontWeight.Bold

        )

    }

}