package com.yusuffdllh.smartfinance.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.yusuffdllh.smartfinance.data.model.User

object DummyUserRepository {

    var user by mutableStateOf(

        User(

            username = "Yusuf Fadilah",

            email = "yusuf@email.com"

        )

    )

    fun updateUser(

        username: String,

        email: String

    ) {

        user = User(

            username = username,

            email = email

        )

    }

}