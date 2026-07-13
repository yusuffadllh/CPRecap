package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Border
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.Transparent

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit
) {

    OutlinedButton(

        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),

        shape = RoundedCornerShape(16.dp),

        border = BorderStroke(
            1.dp,
            Border
        ),

        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Transparent,
            contentColor = TextPrimary
        )

    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )

    }

}