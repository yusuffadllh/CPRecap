package com.yusuffdllh.smartfinance.data.model

import com.yusuffdllh.smartfinance.data.local.entity.BudgetEntity
import com.yusuffdllh.smartfinance.data.local.entity.CategoryEntity
import com.yusuffdllh.smartfinance.data.local.entity.TransactionEntity

data class BackupData(
    val transactions: List<TransactionEntity>,
    val budgets: List<BudgetEntity>,
    val categories: List<CategoryEntity>
)
