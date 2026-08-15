package com.yusuffdllh.smartfinance.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import com.yusuffdllh.smartfinance.data.model.Category
import com.yusuffdllh.smartfinance.ui.theme.*

object DummyCategoryRepository {

    val expenseCategories = listOf(
        Category(1, "Makanan", Icons.Default.Fastfood, Primary),
        Category(2, "Transportasi", Icons.Default.DirectionsBus, ChartBlue),
        Category(3, "Belanja", Icons.Default.ShoppingBag, Color(0xFF8B5CF6)),
        Category(4, "Tagihan", Icons.AutoMirrored.Filled.ReceiptLong, Warning),
        Category(5, "Hiburan", Icons.Default.Movie, Danger),
        Category(6, "Kesehatan", Icons.Default.LocalHospital, Danger),
        Category(7, "Pendidikan", Icons.Default.School, Secondary),
        Category(13, "Transfer Keluar", Icons.AutoMirrored.Filled.Send, Danger)
    )

    val incomeCategories = listOf(
        Category(8, "Gaji", Icons.Default.AttachMoney, Primary),
        Category(9, "Tabungan", Icons.Default.AccountBalanceWallet, Secondary),
        Category(10, "Bonus", Icons.Default.CardGiftcard, Warning),
        Category(11, "Investasi", Icons.AutoMirrored.Filled.TrendingUp, ChartBlue),
        Category(14, "Transfer Masuk", Icons.AutoMirrored.Filled.CallReceived, Primary),
        Category(12, "Lainnya", Icons.Default.MoreHoriz, TextSecondary)
    )

    // Keeping for compatibility but favoring type-specific ones
    val categories = mutableStateListOf<Category>().apply {
        addAll(expenseCategories)
        addAll(incomeCategories)
    }

    fun getCategoryByName(name: String): Category? {
        return categories.find { it.name == name }
    }
}
