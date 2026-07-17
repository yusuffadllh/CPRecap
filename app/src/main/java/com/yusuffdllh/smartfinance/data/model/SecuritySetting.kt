package com.yusuffdllh.smartfinance.data.model

data class SecuritySetting(

    val pinEnabled: Boolean,

    val twoFactorEnabled: Boolean,

    val lastLogin: String

)