package com.yusuffdllh.smartfinance.data.repository

import com.yusuffdllh.smartfinance.data.model.Device
import com.yusuffdllh.smartfinance.data.model.SecuritySetting

object DummySecurityRepository {

    fun getSecuritySetting(): SecuritySetting {

        return SecuritySetting(

            pinEnabled = true,

            twoFactorEnabled = true,

            lastLogin = "25 Mei 2024, 12:30"

        )

    }

    fun getDevices(): List<Device> {

        return listOf(

            Device(

                name = "iPhone 14 Pro",

                location = "Jakarta, Indonesia",

                lastActive = "Aktif",

                active = true

            ),

            Device(

                name = "MacBook Pro",

                location = "Jakarta, Indonesia",

                lastActive = "25 Mei 2024, 10:15",

                active = false

            ),

            Device(

                name = "Windows PC",

                location = "Bandung, Indonesia",

                lastActive = "24 Mei 2024, 18:45",

                active = false

            )

        )

    }

}