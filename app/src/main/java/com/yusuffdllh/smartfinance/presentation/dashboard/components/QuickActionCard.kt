package com.yusuffdllh.smartfinance.presentation.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun QuickActionCard(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionItem(
                icon = Icons.Outlined.Add,
                label = "Tambah",
                onClick = { navController.navigate(Screen.AddTransaction.route) }
            )
            ActionItem(
                icon = Icons.Outlined.History,
                label = "Riwayat",
                onClick = { navController.navigate(Screen.Transaction.route) }
            )
            ActionItem(
                icon = Icons.Outlined.Analytics,
                label = "Laporan",
                onClick = { navController.navigate(Screen.Analytics.route) }
            )
            ActionItem(
                icon = Icons.Outlined.Wallet,
                label = "Anggaran",
                onClick = { navController.navigate(Screen.Budget.route) }
            )
        }
    }
}

@Composable
private fun ActionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(64.dp)
    ) {
        FilledTonalIconButton(
            onClick = onClick,
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = Primary.copy(alpha = 0.1f),
                contentColor = Primary
            )
        ) {
            Icon(imageVector = icon, contentDescription = label)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = TextSecondary, fontSize = 12.sp)
    }
}
