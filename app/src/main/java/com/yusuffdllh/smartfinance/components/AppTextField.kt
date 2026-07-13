package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = ""
) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        modifier = Modifier.fillMaxWidth(),

        singleLine = true,

        label = {
            Text(label)
        },

        placeholder = {
            Text(
                placeholder,
                color = TextSecondary
            )
        },

        shape = RoundedCornerShape(16.dp),

        textStyle = MaterialTheme.typography.bodyLarge,

        colors = OutlinedTextFieldDefaults.colors(

            focusedContainerColor = Surface,

            unfocusedContainerColor = Surface,

            focusedBorderColor = Primary,

            unfocusedBorderColor = Border,

            focusedTextColor = TextPrimary,

            unfocusedTextColor = TextPrimary,

            cursorColor = Primary

        )

    )

}