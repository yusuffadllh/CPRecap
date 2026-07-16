package com.yusuffdllh.smartfinance.utils

import java.text.NumberFormat
import java.util.Locale

fun Long.toCurrency(): String {

    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

    formatter.maximumFractionDigits = 0

    return formatter.format(this)

}