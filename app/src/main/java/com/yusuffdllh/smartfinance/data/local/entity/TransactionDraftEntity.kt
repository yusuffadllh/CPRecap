package com.yusuffdllh.smartfinance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_drafts")
data class TransactionDraftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val merchant: String,
    val amount: Long,
    val date: Long,
    val reference: String,
    val type: String, // "INCOME" or "EXPENSE"
    val category: String = "Umum",
    val isSynced: Boolean = false
)
