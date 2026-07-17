package com.yusuffdllh.smartfinance.screen.settings.security.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.screen.setting.security.components.DeviceSection
import com.yusuffdllh.smartfinance.ui.theme.Divider
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun SecuritySection(

    onChangePasswordClick: () -> Unit = {}

) {

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

                text = "Keamanan Akun",

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold,

                color = TextPrimary

            )

            Spacer(

                modifier = Modifier.height(16.dp)

            )

            SecurityItem(

                icon = Icons.Outlined.Lock,

                title = "Ubah Password",

                onClick = onChangePasswordClick

            )

            SecurityItem(

                icon = Icons.Outlined.Security,

                title = "PIN",

                trailing = "Aktif",

                trailingColor = Primary,

                onClick = {}

            )

            SecurityItem(

                icon = Icons.Outlined.VerifiedUser,

                title = "Autentikasi Dua Faktor",

                trailing = "Aktif",

                trailingColor = Primary,

                showDivider = false,

                onClick = {}

            )

            HorizontalDivider(color = Divider)

            Spacer(modifier = Modifier.height(16.dp))

            Text(

                text = "Login Terakhir",

                style = MaterialTheme.typography.bodyMedium,

                color = TextSecondary

            )

            Spacer(

                modifier = Modifier.height(6.dp)

            )

            Text(

                text = "25 Mei 2024, 12:30",

                style = MaterialTheme.typography.bodyLarge,

                fontWeight = FontWeight.Medium,

                color = TextPrimary

            )

        }

    }

    Spacer(

        modifier = Modifier.height(20.dp)

    )

    DeviceSection()

}