package com.yusuffdllh.smartfinance.presentation.setting.security

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.presentation.profile.ProfileViewModel
import com.yusuffdllh.smartfinance.presentation.setting.account.AccountUiState
import com.yusuffdllh.smartfinance.presentation.setting.account.AccountViewModel
import com.yusuffdllh.smartfinance.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(
    navController: NavController,
    accountViewModel: AccountViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by accountViewModel.uiState.collectAsState()
    
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

    if (showChangePasswordDialog) {
        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            title = { Text("Ubah Kata Sandi") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Kata Sandi Baru") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Konfirmasi Kata Sandi") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPassword == confirmPassword && newPassword.length >= 6) {
                            accountViewModel.changePassword(newPassword)
                            showChangePasswordDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Ubah")
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) { Text("Batal") }
            },
            containerColor = Surface
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Keamanan", color = TextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Section: Password
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Lock, contentDescription = null, tint = Primary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Kata Sandi", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Ubah kata sandi secara berkala untuk menjaga keamanan akun kamu.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showChangePasswordDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Border),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Ganti Kata Sandi", color = TextPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section: Sessions
            Text("Sesi Login", style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Devices, contentDescription = null, tint = Primary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Perangkat Ini", style = MaterialTheme.typography.bodyLarge, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                            Text("Aktif sekarang", style = MaterialTheme.typography.bodySmall, color = Primary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    profileViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Danger),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Keluar dari Semua Perangkat", color = TextPrimary, fontWeight = FontWeight.Bold)
            }
            
            if (uiState is AccountUiState.Loading) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary)
                }
            }
        }
    }
}
