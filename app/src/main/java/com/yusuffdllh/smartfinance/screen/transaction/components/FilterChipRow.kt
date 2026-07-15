package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun FilterChipRow(

    selected: String,

    onSelectedChange: (String) -> Unit

) {

    val filters = listOf(

        "Semua",

        "Pemasukan",

        "Pengeluaran"

    )

    Row(

        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ) {

        filters.forEach { filter ->

            FilterChip(

                selected = selected == filter,

                onClick = {

                    onSelectedChange(filter)

                },

                label = {

                    Text(

                        text = filter,

                        color = if (selected == filter)
                            TextPrimary
                        else
                            TextSecondary

                    )

                },

                colors = FilterChipDefaults.filterChipColors(

                    selectedContainerColor = Primary,

                    containerColor = Surface,

                    selectedLabelColor = TextPrimary,

                    labelColor = TextSecondary

                )

            )

        }

    }

}