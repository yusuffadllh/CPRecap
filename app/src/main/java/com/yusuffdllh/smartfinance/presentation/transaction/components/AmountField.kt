package com.yusuffdllh.smartfinance.presentation.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun AmountField(
    amount: String,
    onAmountChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Nominal",
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = amount,
            onValueChange = {
                val filtered = it.filter { char -> char.isDigit() }
                onAmountChange(filtered)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            cursorBrush = SolidColor(Primary),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Surface, RoundedCornerShape(18.dp))
                        .padding(horizontal = 18.dp, vertical = 18.dp)
                ) {
                    if (amount.isEmpty()) {
                        Text(
                            text = "Rp0",
                            color = TextHint,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "Rp${formatCurrency(amount.toLongOrNull() ?: 0L)}",
                            color = TextPrimary,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(modifier = Modifier.alpha(0f)) {
                        innerTextField()
                    }
                }
            }
        )
    }
}

private fun formatCurrency(value: Long): String {
    return value.toString().reversed().chunked(3).joinToString(".").reversed()
}
