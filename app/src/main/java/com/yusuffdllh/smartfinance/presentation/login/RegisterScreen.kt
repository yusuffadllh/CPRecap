package com.yusuffdllh.smartfinance.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.components.*
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is RegisterUiState.Success) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_cprecap),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Daftar Akun",
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Buat akun untuk mulai mengelola keuangan.",
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        AppTextField(
            value = name,
            onValueChange = { name = it },
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap"
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Masukkan email"
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = password,
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (uiState is RegisterUiState.Loading) {
            CircularProgressIndicator(color = Primary)
        } else {
            PrimaryButton(
                text = "Daftar",
                onClick = { viewModel.signUp(email, password, name) }
            )
        }

        if (uiState is RegisterUiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (uiState as RegisterUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text("Sudah punya akun? ", color = TextSecondary)
            TextButton(
                onClick = { navController.popBackStack() },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "Masuk",
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
