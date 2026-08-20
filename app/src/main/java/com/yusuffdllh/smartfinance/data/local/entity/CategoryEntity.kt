package com.yusuffdllh.smartfinance.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val name: String,
    val icon: String,
    val color: String,
    val isIncome: Boolean = false,
    val sortOrder: Int = 0
)
