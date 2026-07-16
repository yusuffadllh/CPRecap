package com.yusuffdllh.smartfinance.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.vector.ImageVector
import com.yusuffdllh.smartfinance.data.model.TransactionType

data class TransactionItemModel(

    val id: Int,

    val title: String,

    val amount: String,

    val income: Boolean,

    val category: String,

    val date: String,

    val month: String,

    val note: String = "",

    val icon: ImageVector

) {
    val type: TransactionType get() = if (income) TransactionType.INCOME else TransactionType.EXPENSE
}

object DummyRepository {

    val transactions = mutableStateListOf(

        TransactionItemModel(

            id = 1,

            title = "Gaji Bulanan",

            amount = "+Rp8.000.000",

            income = true,

            category = "Gaji",

            date = "01 Juli 2026",

            month = "Juli 2026",

            icon = Icons.Default.AttachMoney

        ),

        TransactionItemModel(

            id = 2,

            title = "Makan Siang",

            amount = "-Rp45.000",

            income = false,

            category = "Makanan",

            date = "03 Juli 2026",

            month = "Juli 2026",

            icon = Icons.Default.Fastfood

        ),

        TransactionItemModel(

            id = 3,

            title = "Naik Bus",

            amount = "-Rp12.000",

            income = false,

            category = "Transportasi",

            date = "04 Juli 2026",

            month = "Juli 2026",

            icon = Icons.Default.DirectionsBus

        ),

        TransactionItemModel(

            id = 4,

            title = "Belanja Bulanan",

            amount = "-Rp530.000",

            income = false,

            category = "Belanja",

            date = "06 Juli 2026",

            month = "Juli 2026",

            icon = Icons.Default.ShoppingBag

        )

    )

    fun addTransaction(

        title: String,

        amount: String,

        income: Boolean,

        category: String,

        date: String,

        note: String

    ) {

        val icon = when (category) {

            "Makanan" -> Icons.Default.Fastfood

            "Transportasi" -> Icons.Default.DirectionsBus

            "Belanja" -> Icons.Default.ShoppingBag

            "Tagihan" -> Icons.AutoMirrored.Filled.ReceiptLong

            "Hiburan" -> Icons.Default.Movie

            "Gaji" -> Icons.Default.AttachMoney

            else -> Icons.Default.AttachMoney

        }

        val month = date.substringAfter(" ")

        transactions.add(

            0,

            TransactionItemModel(

                id = transactions.size + 1,

                title = title,

                amount = if (income)
                    "+Rp$amount"
                else
                    "-Rp$amount",

                income = income,

                category = category,

                date = date,

                month = month,

                note = note,

                icon = icon

            )

        )

    }

}