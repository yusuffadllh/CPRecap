package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun MonthHeader(
    month: String
) {

    Text(
        text = month,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
    )

}