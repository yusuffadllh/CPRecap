package com.yusuffdllh.smartfinance.screen.profile.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Surface

@Composable
fun ProfileMenuCard(

    onAccountClick: () -> Unit,

    onSecurityClick: () -> Unit,

    onBackupClick: () -> Unit,

    onNotificationClick: () -> Unit,

    onThemeClick: () -> Unit,

    onAboutClick: () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(

            containerColor = Surface

        )

    ) {

        Column {

            ProfileMenuItem(

                icon = Icons.Outlined.Settings,

                title = "Pengaturan Akun",

                onClick = onAccountClick

            )

            ProfileMenuItem(

                icon = Icons.Outlined.Security,

                title = "Keamanan",

                onClick = onSecurityClick

            )

            ProfileMenuItem(

                icon = Icons.Outlined.Storage,

                title = "Backup & Restore",

                onClick = onBackupClick

            )

            ProfileMenuItem(

                icon = Icons.Outlined.Notifications,

                title = "Notifikasi",

                onClick = onNotificationClick

            )

            ProfileMenuItem(

                icon = Icons.Outlined.Palette,

                title = "Tema",

                trailingText = "Gelap",

                onClick = onThemeClick

            )

            ProfileMenuItem(

                icon = Icons.Outlined.Info,

                title = "Tentang Aplikasi",

                showDivider = false,

                onClick = onAboutClick

            )

        }

    }

}