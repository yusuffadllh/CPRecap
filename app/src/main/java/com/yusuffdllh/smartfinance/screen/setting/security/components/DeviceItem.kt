package com.yusuffdllh.smartfinance.screen.setting.security.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Divider
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun DeviceItem(

    icon: ImageVector,

    deviceName: String,

    location: String,

    status: String,

    showDivider: Boolean = true

) {

    val statusColor =

        if (status == "Aktif") Primary else TextSecondary

    Column {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(

                imageVector = icon,

                contentDescription = null,

                tint = TextPrimary

            )

            Spacer(

                modifier = Modifier.width(16.dp)

            )

            Column(

                modifier = Modifier.weight(1f)

            ) {

                Text(

                    text = deviceName,

                    style = MaterialTheme.typography.bodyLarge,

                    fontWeight = FontWeight.SemiBold,

                    color = TextPrimary

                )

                Spacer(

                    modifier = Modifier.height(2.dp)

                )

                Text(

                    text = location,

                    style = MaterialTheme.typography.bodyMedium,

                    color = TextSecondary

                )

            }

            Text(

                text = status,

                style = MaterialTheme.typography.bodyMedium,

                fontWeight = FontWeight.Medium,

                color = statusColor

            )

        }

        if (showDivider) {

            HorizontalDivider(

                color = Divider

            )

        }

    }

}