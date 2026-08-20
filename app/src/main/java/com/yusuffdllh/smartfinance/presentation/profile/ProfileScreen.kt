package com.yusuffdllh.smartfinance.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yusuffdllh.smartfinance.components.ProfileMenuItem
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.presentation.dashboard.components.DashboardBottomBar
import com.yusuffdllh.smartfinance.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is ProfileUiState.LoggedOut) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil Saya", color = TextPrimary, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        bottomBar = { DashboardBottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            if (uiState is ProfileUiState.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    color = Primary,
                    trackColor = Surface
                )
            }

            val user = (uiState as? ProfileUiState.Success)?.user

            // User Info Card (Redesigned: Photo Left, Info Right)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (user?.photoUrl?.isNotEmpty() == true) {
                            AsyncImage(
                                model = user.photoUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(20.dp))
                    
                    Column {
                        Text(
                            text = user?.name ?: "User",
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = user?.email ?: "",
                            color = TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Pengaturan", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            
            ProfileMenuItem(
                icon = Icons.Outlined.AccountCircle,
                title = "Informasi Akun",
                onClick = { navController.navigate(Screen.Account.route) }
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Security,
                title = "Keamanan",
                onClick = { navController.navigate(Screen.Security.route) }
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Notifications,
                title = "Notifikasi",
                onClick = { navController.navigate(Screen.Notification.route) }
            )
            ProfileMenuItem(
                icon = Icons.AutoMirrored.Outlined.EventNote,
                title = "Tagihan Terjadwal",
                onClick = { navController.navigate(Screen.ScheduledBill.route) }
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Backup,
                title = "Backup & Restore",
                onClick = { navController.navigate(Screen.Backup.route) }
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Info,
                title = "Tentang Aplikasi",
                onClick = { navController.navigate(Screen.About.route) }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            TextButton(
                onClick = { viewModel.logout() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = Danger)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Outlined.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Keluar dari Akun")
            }
        }
    }
}
