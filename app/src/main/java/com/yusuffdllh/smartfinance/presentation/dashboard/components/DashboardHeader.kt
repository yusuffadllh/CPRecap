package com.yusuffdllh.smartfinance.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun DashboardHeader(
    userName: String = "User",
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Halo, $userName!",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Kelola keuanganmu dengan bijak",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Surface)
                .clickable { onNotificationClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifikasi",
                tint = TextPrimary,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
