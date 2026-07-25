package com.yusuffdllh.smartfinance.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val photoUrl: String = "",
    val photo: Int? = null
)
