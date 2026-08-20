package com.yusuffdllh.smartfinance.presentation.dashboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun DashboardBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Background),
            border = BorderStroke(1.dp, Border),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(
                    icon = Icons.Default.Home,
                    label = "Beranda",
                    selected = currentRoute == Screen.Dashboard.route,
                    onClick = {
                        if (currentRoute != Screen.Dashboard.route) {
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Dashboard.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                )
                BottomNavItem(
                    icon = Icons.AutoMirrored.Filled.ReceiptLong,
                    label = "Transaksi",
                    selected = currentRoute == Screen.Transaction.route,
                    onClick = {
                        if (currentRoute != Screen.Transaction.route) {
                            navController.navigate(Screen.Transaction.route) {
                                launchSingleTop = true
                            }
                        }
                    }
                )
                
                // Gap for FAB
                Spacer(modifier = Modifier.width(56.dp))
                
                BottomNavItem(
                    icon = Icons.Default.Analytics,
                    label = "Laporan",
                    selected = currentRoute == Screen.Analytics.route,
                    onClick = {
                        if (currentRoute != Screen.Analytics.route) {
                            navController.navigate(Screen.Analytics.route) {
                                launchSingleTop = true
                            }
                        }
                    }
                )
                BottomNavItem(
                    icon = Icons.Default.Person,
                    label = "Profil",
                    selected = currentRoute == Screen.Profile.route,
                    onClick = {
                        if (currentRoute != Screen.Profile.route) {
                            navController.navigate(Screen.Profile.route) {
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
        
        // Central FAB
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-24).dp)
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddTransaction.route) },
                containerColor = Primary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(64.dp),
                elevation = FloatingActionButtonDefaults.elevation(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah", modifier = Modifier.size(36.dp))
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) Primary else TextSecondary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (selected) Primary else TextSecondary,
            style = MaterialTheme.typography.labelLarge,
            fontSize = 10.sp
        )
    }
}
