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
fun NoteField(

    note: String,

    onNoteChange: (String) -> Unit

) {

    Column {

        Text(

            text = "Catatan (Opsional)",

            color = TextSecondary,

            style = MaterialTheme.typography.bodyMedium,

            fontWeight = FontWeight.Medium

        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(

            value = note,

            onValueChange = onNoteChange,

            textStyle = TextStyle(

                color = TextPrimary,

                fontSize = 16.sp

            ),

            keyboardOptions = KeyboardOptions(

                capitalization = KeyboardCapitalization.Sentences

            ),

            cursorBrush = SolidColor(Primary),

            decorationBox = { innerTextField ->

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            Surface,
                            RoundedCornerShape(18.dp)
                        )
                        .padding(18.dp)

                ) {

                    if (note.isEmpty()) {

                        Text(

                            text = "Tambahkan catatan...",

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