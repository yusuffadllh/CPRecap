package com.yusuffdllh.smartfinance.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "budgets",
    primaryKeys = ["userId", "category"]
)
data class BudgetEntity(
    // Defaults so Firestore toObject() can use a no-arg constructor.
    val userId: String = "",
    val category: String = "",
    val amount: Long = 0,
    val period: String = "MONTHLY",
    val isSynced: Boolean = false
)
