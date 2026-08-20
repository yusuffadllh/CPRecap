package com.yusuffdllh.smartfinance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    // Defaults on every field so Firestore's reflective deserializer
    // (toObject) can use the generated no-arg constructor.
    val userId: String = "",
    val title: String = "",
    val amount: Long = 0,
    val category: String = "",
    val date: Long = 0,
    val type: String = "EXPENSE", // "INCOME" or "EXPENSE"
    val note: String? = null,
    val isSynced: Boolean = false
)
