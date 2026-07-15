package com.yusuffdllh.smartfinance.data.model

enum class TransactionType {
    INCOME,
    EXPENSE
}

data class Transaction(

    val id: Int,

    val title: String,

    val amount: Long,

    val category: String,

    val date: String,

    val note: String = "",

    val type: TransactionType

)