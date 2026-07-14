package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun DividerText(
    text: String = "atau masuk dengan"
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.width(12.dp))

        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )
    }
}