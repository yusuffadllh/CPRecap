package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun SearchBar(

    value: String,

    onValueChange: (String) -> Unit

) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        modifier = Modifier.fillMaxWidth(),

        singleLine = true,

        shape = RoundedCornerShape(16.dp),

        placeholder = {

            Text(

                text = "Cari transaksi...",

                color = TextSecondary

            )

        },

        leadingIcon = {

            Icon(

                imageVector = Icons.Default.Search,

                contentDescription = null,

                tint = TextSecondary

            )

        },

        colors = OutlinedTextFieldDefaults.colors(

            focusedContainerColor = Surface,

            unfocusedContainerColor = Surface,

            disabledContainerColor = Surface,

            focusedBorderColor = Primary,

            unfocusedBorderColor = Surface,

            focusedTextColor = TextPrimary,

            unfocusedTextColor = TextPrimary,

            cursorColor = Primary

        )

    )

}