package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun DateField(

    date: String,

    onClick: () -> Unit

) {

    Column {

        Text(

            text = "Tanggal",

            color = TextSecondary,

            style = MaterialTheme.typography.bodyMedium,

            fontWeight = FontWeight.Medium

        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(

            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                    onClick()

                },

            shape = RoundedCornerShape(18.dp),

            colors = CardDefaults.cardColors(

                containerColor = Surface

            )

        ) {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 18.dp,
                        vertical = 18.dp
                    ),

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Box(

                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                Primary.copy(alpha = .15f),
                                RoundedCornerShape(12.dp)
                            ),

                        contentAlignment = Alignment.Center

                    ) {

                        Icon(

                            imageVector = Icons.Outlined.DateRange,

                            contentDescription = null,

                            tint = Primary

                        )

                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Text(

                        text = date,

                        color = TextPrimary,

                        style = MaterialTheme.typography.bodyLarge,

                        fontWeight = FontWeight.SemiBold

                    )

                }

                Icon(

                    imageVector = Icons.Default.ChevronRight,

                    contentDescription = null,

                    tint = TextSecondary

                )

            }

        }

    }

}