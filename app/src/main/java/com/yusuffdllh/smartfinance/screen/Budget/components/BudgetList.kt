package com.yusuffdllh.smartfinance.screen.budget.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.data.model.Budget

@Composable
fun BudgetList(

    budgets: List<Budget>

) {

    Column(

        modifier = Modifier.fillMaxWidth(),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {

        budgets.forEach { budget ->

            BudgetItem(

                budget = budget

            )

        }

    }

}