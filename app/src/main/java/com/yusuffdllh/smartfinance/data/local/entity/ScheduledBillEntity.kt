package com.yusuffdllh.smartfinance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scheduled_bills")
data class ScheduledBillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    // Defaults so Firestore toObject() can use a no-arg constructor.
    val userId: String = "",
    val name: String = "",
    val amount: Long = 0,
    val category: String = "",
    val dueDate: Int = 1, // 1-31
    val isAutoPaid: Boolean = true,
    val lastGeneratedMonthYear: String = "", // Format: "MM-yyyy"
    val isSynced: Boolean = false
)
