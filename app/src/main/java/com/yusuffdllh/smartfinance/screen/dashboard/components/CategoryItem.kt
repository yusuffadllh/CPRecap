package com.yusuffdllh.smartfinance.screen.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import androidx.compose.ui.draw.clip

@Composable
fun CategoryItem(

    title: String,

    percent: String,

    color: Color

) {

    Row(

        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier.width(180.dp)

    ) {

        Box(

            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)

        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(

            text = title,

            modifier = Modifier.weight(1f),

            color = TextPrimary

        )

        Text(

            text = percent,

            fontWeight = FontWeight.Bold,

            color = TextPrimary

        )

    }

}