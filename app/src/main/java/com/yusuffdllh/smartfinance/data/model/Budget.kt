package com.yusuffdllh.smartfinance.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Budget(

    val id: Int,

    val category: String,

    val budget: Long,

    val spent: Long,

    val icon: ImageVector,

    val color: Color

) {

    val percentage: Int
        get() = if (budget == 0L) 0 else ((spent * 100) / budget).toInt()

}