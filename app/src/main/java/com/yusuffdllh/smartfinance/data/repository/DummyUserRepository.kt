package com.yusuffdllh.smartfinance.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.yusuffdllh.smartfinance.data.model.User

object DummyUserRepository {

    var user by mutableStateOf(

        User(

            username = "Yusuf Fadilah",

            email = "yusuf@email.com",

            phone = "081234567890",

            birthDate = "25 Juli 2004",

            gender = "Laki-laki",

            photo = null

        )

    )

    fun updateUser(

        username: String,

        email: String,

        phone: String,

        birthDate: String,

        gender: String,

        photo: Int? = null

    ) {

        user = User(

            username = username,

            email = email,

            phone = phone,

            birthDate = birthDate,

            gender = gender,

            photo = photo

        )

    }

}