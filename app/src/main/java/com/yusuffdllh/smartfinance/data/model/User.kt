package com.yusuffdllh.smartfinance.data.model

data class User(

    val username: String,

    val email: String,

    val phone: String,

    val birthDate: String,

    val gender: String,

    val photo: Int? = null

)