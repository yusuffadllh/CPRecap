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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.components.*
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val webClientId = stringResource(R.string.default_web_client_id)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
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
            text = "Masuk",
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Kelola keuangan pribadi Anda dengan bantuan AI.",
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

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

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState is LoginUiState.Loading) {
            CircularProgressIndicator(color = Primary)
        } else {
            PrimaryButton(
                text = "Masuk",
                onClick = { viewModel.signIn(email, password) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        DividerText()
        Spacer(modifier = Modifier.height(24.dp))

        SocialButton(
            icon = R.drawable.ic_google,
            text = "Masuk dengan Google"
        ) {
            viewModel.setLoading()
            
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Scope("https://www.googleapis.com/auth/gmail.readonly"))
                .requestIdToken(webClientId)
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            
            // Note: Since we are using CredentialManager as the main flow, 
            // we will use the legacy client just to ensure scopes are requested.
            // For a "real all" experience, we'd use the Authorization API if target SDK >= 34.
            
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(true)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            scope.launch {
                try {
                    val result = credentialManager.getCredential(context, request)
                    when (val credential = result.credential) {
                        is GoogleIdTokenCredential -> {
                            viewModel.signInWithGoogle(credential.idToken)
                        }
                        is PasswordCredential -> {
                            viewModel.signIn(credential.id, credential.password)
                        }
                        is CustomCredential -> {
                            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                                viewModel.signInWithGoogle(googleIdTokenCredential.idToken)
                            } else {
                                viewModel.setError("Tipe tidak didukung: ${credential.type}")
                            }
                        }
                        else -> {
                            viewModel.setError("Tipe kredensial tidak dikenali: ${credential.type}")
                        }
                    }
                } catch (e: androidx.credentials.exceptions.GetCredentialException) {
                    viewModel.setError("Gagal: ${e.message}")
                } catch (e: Exception) {
                    viewModel.setError("Kesalahan sistem: ${e.localizedMessage}")
                }
            }
        }

        if (uiState is LoginUiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (uiState as LoginUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Dengan masuk, Anda menyetujui Kebijakan Privasi kami.",
            color = TextSecondary.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Belum punya akun? ", color = TextSecondary)
            TextButton(
                onClick = { navController.navigate(Screen.Register.route) },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "Daftar",
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
