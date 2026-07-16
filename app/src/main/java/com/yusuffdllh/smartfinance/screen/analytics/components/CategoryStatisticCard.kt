package com.yusuffdllh.smartfinance.screen.analytics.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.data.repository.TransactionItemModel
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun CategoryStatisticCard(

    transactions: List<TransactionItemModel>

) {

    //------------------------------------------------
    // Ambil hanya pengeluaran
    //------------------------------------------------

    val expenseList = transactions.filter {

        !it.income

    }

    //------------------------------------------------
    // Total seluruh pengeluaran
    //------------------------------------------------

    val totalExpense = expenseList.sumOf {

        it.amount
            .replace("-Rp", "")
            .replace(".", "")
            .toLong()

    }

    //------------------------------------------------
    // Helper
    //------------------------------------------------

    fun totalCategory(category: String): Long {

        return expenseList

            .filter {

                it.category == category

            }

            .sumOf {

                it.amount
                    .replace("-Rp", "")
                    .replace(".", "")
                    .toLong()

            }

    }

    fun percentage(category: String): Int {

        if (totalExpense == 0L) return 0

        return ((totalCategory(category) * 100) / totalExpense).toInt()

    }

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)

        ) {

            //------------------------------------------------
            // Header
            //------------------------------------------------

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {

                Text(

                    text = "Pengeluaran per Kategori",

                    color = TextPrimary,

                    fontSize = 20.sp,

                    fontWeight = FontWeight.Bold

                )

                TextButton(

                    onClick = { }

                ) {

                    Text(

                        text = "Lihat Semua",

                        color = Secondary

                    )

                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            //------------------------------------------------
            // Makanan
            //------------------------------------------------

            CategoryStatisticItem(

                title = "Makanan",

                amount = totalCategory("Makanan"),

                percentage = percentage("Makanan"),

                color = Primary,

                icon = "🍽"

            )

            HorizontalDivider(color = Border)

            //------------------------------------------------
            // Transportasi
            //------------------------------------------------

            CategoryStatisticItem(

                title = "Transportasi",

                amount = totalCategory("Transportasi"),

                percentage = percentage("Transportasi"),

                color = ChartBlue,

                icon = "🚌"

            )

            HorizontalDivider(color = Border)

            //------------------------------------------------
            // Belanja
            //------------------------------------------------

            CategoryStatisticItem(

                title = "Belanja",

                amount = totalCategory("Belanja"),

                percentage = percentage("Belanja"),

                color = ChartPurple,

                icon = "🛍"

            )

            HorizontalDivider(color = Border)

            //------------------------------------------------
            // Tagihan
            //------------------------------------------------

            CategoryStatisticItem(

                title = "Tagihan",

                amount = totalCategory("Tagihan"),

                percentage = percentage("Tagihan"),

                color = ChartOrange,

                icon = "💡"

            )

            HorizontalDivider(color = Border)

            //------------------------------------------------
            // Hiburan
            //------------------------------------------------

            CategoryStatisticItem(

                title = "Hiburan",

                amount = totalCategory("Hiburan"),

                percentage = percentage("Hiburan"),

                color = Danger,

                icon = "🎮"

            )

        }

    }

}