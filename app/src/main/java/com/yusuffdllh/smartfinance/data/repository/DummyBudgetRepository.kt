package com.yusuffdllh.smartfinance.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.runtime.mutableStateListOf
import com.yusuffdllh.smartfinance.data.model.Budget
import com.yusuffdllh.smartfinance.ui.theme.ChartBlue
import com.yusuffdllh.smartfinance.ui.theme.ChartPurple
import com.yusuffdllh.smartfinance.ui.theme.ChartOrange
import com.yusuffdllh.smartfinance.ui.theme.Danger
import com.yusuffdllh.smartfinance.ui.theme.Primary

object DummyBudgetRepository {

    val budgets = mutableStateListOf(

        Budget(
            id = 1,
            category = "Makanan",
            budget = 5_000_000,
            spent = 3_000_000,
            icon = Icons.Default.Fastfood,
            color = Primary
        ),

        Budget(
            id = 2,
            category = "Transportasi",
            budget = 1_000_000,
            spent = 750_000,
            icon = Icons.Default.DirectionsBus,
            color = ChartBlue
        ),

        Budget(
            id = 3,
            category = "Belanja",
            budget = 1_000_000,
            spent = 670_000,
            icon = Icons.Default.ShoppingBag,
            color = ChartPurple
        ),

        Budget(
            id = 4,
            category = "Hiburan",
            budget = 500_000,
            spent = 200_000,
            icon = Icons.Default.MusicNote,
            color = ChartOrange
        ),

        Budget(
            id = 5,
            category = "Tagihan",
            budget = 500_000,
            spent = 400_000,
            icon = Icons.Default.Receipt,
            color = Danger
        )

    )

    fun totalBudget(): Long {

        return budgets.sumOf {

            it.budget

        }

    }

    fun totalSpent(): Long {

        return budgets.sumOf {

            it.spent

        }

    }

    fun totalPercentage(): Int {

        val budget = totalBudget()

        if (budget == 0L) return 0

        return ((totalSpent() * 100) / budget).toInt()

    }

}