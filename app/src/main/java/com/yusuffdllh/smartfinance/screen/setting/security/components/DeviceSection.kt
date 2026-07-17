package com.yusuffdllh.smartfinance.screen.setting.security.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.LaptopMac
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary

@Composable
fun DeviceSection() {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Column(

            modifier = Modifier.padding(20.dp)

        ) {

            Text(

                text = "Perangkat Aktif",

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold,

                color = TextPrimary

            )

            Spacer(

                modifier = Modifier.height(16.dp)

            )

            DeviceItem(

                icon = Icons.Outlined.PhoneAndroid,

                deviceName = "iPhone 14 Pro",

                location = "Jakarta, Indonesia",

                status = "Aktif"

            )

            DeviceItem(

                icon = Icons.Outlined.LaptopMac,

                deviceName = "MacBook Pro",

                location = "Jakarta, Indonesia",

                status = "25 Mei 2024, 10:15"

            )

            DeviceItem(

                icon = Icons.Outlined.Computer,

                deviceName = "Windows PC",

                location = "Bandung, Indonesia",

                status = "24 Mei 2024, 18:45",

                showDivider = false

            )

        }

    }

}