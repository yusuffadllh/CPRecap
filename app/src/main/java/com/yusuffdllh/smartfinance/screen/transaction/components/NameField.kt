package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun NameField(

    value: String,

    onValueChange: (String) -> Unit

) {

    Column {

        Text(

            text = "Nama",

            color = TextSecondary,

            style = MaterialTheme.typography.bodyMedium,

            fontWeight = FontWeight.Medium

        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(

            value = value,

            onValueChange = onValueChange,

            singleLine = true,

            textStyle = TextStyle(

                color = TextPrimary,

                fontSize = 16.sp

            ),

            cursorBrush = SolidColor(Primary),

            keyboardOptions = KeyboardOptions(

                capitalization = KeyboardCapitalization.Words

            ),

            decorationBox = { innerTextField ->

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Surface,
                            RoundedCornerShape(18.dp)
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 18.dp
                        )

                ) {

                    if (value.isEmpty()) {

                        Text(

                            text = "Contoh: Makan Siang",

                            color = TextHint,

                            fontSize = 16.sp

                        )

                    }

                    innerTextField()

                }

            }

        )

    }

}