package com.yusuffdllh.smartfinance.data.local

import com.yusuffdllh.smartfinance.data.local.entity.CategoryEntity

/**
 * Default set of categories seeded into the Room database on first launch.
 *
 * [CategoryEntity.icon] and [CategoryEntity.color] use stable string keys resolved by
 * [com.yusuffdllh.smartfinance.data.mapper.CategoryMapper]. The list mirrors the previous
 * hardcoded preset so the UI stays visually identical.
 */
object CategoryPresets {

    val categories: List<CategoryEntity> = listOf(
        // Expense
        CategoryEntity("Makanan", "fastfood", "primary", isIncome = false, sortOrder = 0),
        CategoryEntity("Transportasi", "bus", "chart_blue", isIncome = false, sortOrder = 1),
        CategoryEntity("Belanja", "shopping_bag", "chart_purple", isIncome = false, sortOrder = 2),
        CategoryEntity("Tagihan", "receipt", "warning", isIncome = false, sortOrder = 3),
        CategoryEntity("Hiburan", "movie", "danger", isIncome = false, sortOrder = 4),
        CategoryEntity("Kesehatan", "hospital", "danger", isIncome = false, sortOrder = 5),
        CategoryEntity("Pendidikan", "school", "secondary", isIncome = false, sortOrder = 6),
        CategoryEntity("Transfer Keluar", "send", "danger", isIncome = false, sortOrder = 7),
        // Income
        CategoryEntity("Gaji", "money", "primary", isIncome = true, sortOrder = 8),
        CategoryEntity("Tabungan", "wallet", "secondary", isIncome = true, sortOrder = 9),
        CategoryEntity("Bonus", "gift", "warning", isIncome = true, sortOrder = 10),
        CategoryEntity("Investasi", "trending_up", "chart_blue", isIncome = true, sortOrder = 11),
        CategoryEntity("Transfer Masuk", "call_received", "primary", isIncome = true, sortOrder = 12),
        CategoryEntity("Lainnya", "more", "text_secondary", isIncome = true, sortOrder = 13)
    )
}
