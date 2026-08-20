package com.yusuffdllh.smartfinance.presentation.setting.notification

import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Handles the Google consent screen (PendingIntent) result for Gmail authorization.
    val authLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        try {
            val authResult = com.google.android.gms.auth.api.identity.Identity
                .getAuthorizationClient(context)
                .getAuthorizationResultFromIntent(activityResult.data)
            val account = authResult.toGoogleSignInAccount()?.email ?: ""
            viewModel.onGmailAuthorized(account)
        } catch (e: Exception) {
            Log.e("NotificationScreen", "Authorization resolution failed", e)
            viewModel.onGmailAuthorizationFailed(e.localizedMessage ?: "Gagal mengotorisasi Gmail")
        }
    }

    fun startGmailAuthorization() {
        viewModel.setGmailLinking()
        val authorizationRequest = com.google.android.gms.auth.api.identity.AuthorizationRequest.builder()
            .setRequestedScopes(
                listOf(com.google.android.gms.common.api.Scope(com.google.api.services.gmail.GmailScopes.GMAIL_READONLY))
            )
            .build()
        com.google.android.gms.auth.api.identity.Identity.getAuthorizationClient(context)
            .authorize(authorizationRequest)
            .addOnSuccessListener { authorizationResult ->
                if (authorizationResult.hasResolution()) {
                    val pendingIntent = authorizationResult.pendingIntent
                    if (pendingIntent != null) {
                        authLauncher.launch(
                            androidx.activity.result.IntentSenderRequest.Builder(pendingIntent.intentSender).build()
                        )
                    } else {
                        viewModel.onGmailAuthorizationFailed("Tidak ada layar izin yang tersedia.")
                    }
                } else {
                    val email = authorizationResult.toGoogleSignInAccount()?.email ?: ""
                    viewModel.onGmailAuthorized(email)
                }
            }
            .addOnFailureListener { e ->
                Log.e("NotificationScreen", "authorize() failed", e)
                viewModel.onGmailAuthorizationFailed(e.localizedMessage ?: "Gagal menghubungkan Gmail")
            }
    }

    val linkState by viewModel.linkState.collectAsState()
    LaunchedEffect(linkState) {
        when (val state = linkState) {
            is GmailLinkState.Success -> {
                snackbarHostState.showSnackbar("Gmail berhasil dihubungkan")
                viewModel.consumeLinkState()
            }
            is GmailLinkState.Error -> {
                snackbarHostState.showSnackbar("Gagal: ${state.message}")
                viewModel.consumeLinkState()
            }
            else -> {}
        }
    }

    val bankReaderEnabled by viewModel.bankReaderEnabled.collectAsState()
    val emailSyncEnabled by viewModel.emailSyncEnabled.collectAsState()
    val budgetWarningEnabled by viewModel.budgetWarningEnabled.collectAsState()
    val minRp by viewModel.minRp.collectAsState()
    val maxRp by viewModel.maxRp.collectAsState()
    val isGoogleLinked by viewModel.isGoogleLinked.collectAsState()
    val isPermissionGranted by viewModel.isPermissionGranted.collectAsState()
    val lastDetectedPackage by viewModel.lastDetectedPackage.collectAsState()

    // AI (Gemini-compatible) config, seeded from stored values.
    val storedBaseUrl by viewModel.aiBaseUrl.collectAsState()
    val storedApiKey by viewModel.aiApiKey.collectAsState()
    val storedModel by viewModel.aiModel.collectAsState()
    val aiConfigSaved by viewModel.aiConfigSaved.collectAsState()

    var aiBaseUrlInput by remember(storedBaseUrl) { mutableStateOf(storedBaseUrl) }
    var aiApiKeyInput by remember(storedApiKey) { mutableStateOf(storedApiKey) }
    var aiModelInput by remember(storedModel) { mutableStateOf(storedModel) }
    var apiKeyVisible by remember { mutableStateOf(false) }

    LaunchedEffect(aiConfigSaved) {
        if (aiConfigSaved) {
            snackbarHostState.showSnackbar("Pengaturan AI disimpan")
            viewModel.consumeAiConfigSaved()
        }
    }

    val gmailSyncState by viewModel.gmailSyncState.collectAsState()
    LaunchedEffect(gmailSyncState) {
        when (gmailSyncState) {
            is GmailSyncState.Success -> {
                snackbarHostState.showSnackbar("Sinkronisasi email selesai")
                viewModel.consumeGmailSyncState()
            }
            is GmailSyncState.Error -> {
                snackbarHostState.showSnackbar("Sinkronisasi email gagal")
                viewModel.consumeGmailSyncState()
            }
            else -> {}
        }
    }

    // Refresh permission status when returning to screen
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                viewModel.checkPermission()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan Notifikasi", color = TextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
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
            // Permission Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isPermissionGranted) Primary.copy(alpha = 0.1f) else Danger.copy(alpha = 0.1f)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, 
                    if (isPermissionGranted) Primary.copy(alpha = 0.5f) else Danger.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isPermissionGranted) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (isPermissionGranted) Primary else Danger
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isPermissionGranted) "Izin Pembaca Aktif" else "Izin Belum Aktif",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isPermissionGranted) "CPRecap dapat mendeteksi transaksi secara real-time." else "Aktifkan 'Akses Notifikasi' agar deteksi otomatis berfungsi.",
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (!isPermissionGranted) {
                        TextButton(onClick = { 
                            context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                        }) {
                            Text("Aktifkan", color = Danger, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (!isGoogleLinked) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Primary.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Akurasi Tinggi Gmail",
                            color = TextPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hubungkan akun Google kamu untuk membaca struk belanja secara otomatis dengan akurasi 100%.",
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { startGmailAuthorization() },
                            enabled = linkState !is GmailLinkState.Loading,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (linkState is GmailLinkState.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("Hubungkan Gmail", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            NotificationSwitchItem(
                title = "Pembaca Notifikasi Bank",
                description = "Otomatis deteksi transaksi dari notifikasi mobile banking.",
                checked = bankReaderEnabled,
                onCheckedChange = { 
                    viewModel.setBankReader(it) 
                    if (it) {
                        context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                    }
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            NotificationSwitchItem(
                title = "Sinkronisasi Email",
                description = if (isGoogleLinked) "Otomatis cari bukti transaksi dari inbox Gmail." else "Hubungkan akun Google untuk mengaktifkan.",
                checked = emailSyncEnabled && isGoogleLinked,
                enabled = isGoogleLinked,
                onCheckedChange = { viewModel.setEmailSync(it) }
            )
            if (isGoogleLinked) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = { viewModel.syncGmailNow() },
                    enabled = gmailSyncState !is GmailSyncState.Running,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (gmailSyncState is GmailSyncState.Running) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Menyinkronkan...")
                    } else {
                        Text("Sinkronkan Sekarang", fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Rentang Harian (Rp)",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Filter notifikasi berdasarkan nominal transaksi.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = minRp,
                    onValueChange = { viewModel.setRange(it, maxRp) },
                    label = { Text("Min") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = Border,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
                OutlinedTextField(
                    value = maxRp,
                    onValueChange = { viewModel.setRange(minRp, it) },
                    label = { Text("Max") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = Border,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            NotificationSwitchItem(
                title = "Peringatan Anggaran",
                description = "Berikan notifikasi saat pengeluaran mendekati batas anggaran.",
                checked = budgetWarningEnabled,
                onCheckedChange = { viewModel.setBudgetWarning(it) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ---- AI (Gemini) Configuration ----
            Text(
                text = "Pengaturan AI (Gemini)",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Untuk memperkaya deteksi transaksi dari notifikasi. Kosongkan Base URL untuk memakai endpoint Gemini resmi.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = aiBaseUrlInput,
                onValueChange = { aiBaseUrlInput = it },
                label = { Text("Base URL") },
                placeholder = { Text("https://generativelanguage.googleapis.com") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Border,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = aiApiKeyInput,
                onValueChange = { aiApiKeyInput = it },
                label = { Text("API Key") },
                placeholder = { Text("Tempel API key di sini") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (apiKeyVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { apiKeyVisible = !apiKeyVisible }) {
                        Icon(
                            imageVector = if (apiKeyVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (apiKeyVisible) "Sembunyikan" else "Tampilkan",
                            tint = TextSecondary
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Border,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = aiModelInput,
                onValueChange = { aiModelInput = it },
                label = { Text("Model") },
                placeholder = { Text("gemini-1.5-flash") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Border,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.saveAiConfig(aiBaseUrlInput, aiApiKeyInput, aiModelInput)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Simpan Pengaturan AI", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Debug Information
            Text(text = "Informasi Debug (Teknis)", color = TextPrimary, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Surface.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "App Terdeteksi Terakhir:", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
                    Text(text = lastDetectedPackage, color = TextPrimary, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val notificationManager = context.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                    val channelId = "test_channel"
                    val channel = android.app.NotificationChannel(channelId, "Test", android.app.NotificationManager.IMPORTANCE_HIGH)
                    notificationManager.createNotificationChannel(channel)
                    
                    // Case 1: Real Bank Transaction
                    val realNotification = androidx.core.app.NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(com.yusuffdllh.smartfinance.R.drawable.ic_launcher_foreground)
                        .setContentTitle("BCA Mobile")
                        .setContentText("Transfer Berhasil Rp 50.000 ke MIXUE.")
                        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                        .setOnlyAlertOnce(true)
                        .build()
                    
                    // Case 3: GoPay Style
                    val gopayNotification = androidx.core.app.NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(com.yusuffdllh.smartfinance.R.drawable.ic_launcher_foreground)
                        .setContentTitle("GoPay")
                        .setContentText("Kirim saldo Rp 75.000 ke TOKOPEDIA Berhasil.")
                        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                        .setOnlyAlertOnce(true)
                        .build()

                    // Case 3: Micro Transaction
                    val microNotification = androidx.core.app.NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(com.yusuffdllh.smartfinance.R.drawable.ic_launcher_foreground)
                        .setContentTitle("DANA")
                        .setContentText("Pembayaran QRIS Rp 100 Berhasil!")
                        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                        .setOnlyAlertOnce(true)
                        .build()
                    
                    // Case 4: Promotion (Should be ignored)
                    val promoNotification = androidx.core.app.NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(com.yusuffdllh.smartfinance.R.drawable.ic_launcher_foreground)
                        .setContentTitle("Shopee")
                        .setContentText("Promo hemat Rp 500 pakai voucher ini!")
                        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                        .setOnlyAlertOnce(true)
                        .build()
                    
                    // Case 5: Failed Transaction
                    val failedNotification = androidx.core.app.NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(com.yusuffdllh.smartfinance.R.drawable.ic_launcher_foreground)
                        .setContentTitle("BCA Mobile")
                        .setContentText("Transaksi GAGAL. Saldo tidak mencukupi untuk bayar Rp 10.000 ke MIXUE.")
                        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                        .setOnlyAlertOnce(true)
                        .build()

                    // Case 6: Greedy Promo
                    val greedyPromo = androidx.core.app.NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(com.yusuffdllh.smartfinance.R.drawable.ic_launcher_foreground)
                        .setContentTitle("GoPay")
                        .setContentText("Menangkan hadiah 100jt! Klik di sini untuk claim promo untung.")
                        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                        .setOnlyAlertOnce(true)
                        .build()

                    notificationManager.notify(99, realNotification)
                    
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        notificationManager.notify(101, gopayNotification)
                    }, 2000)

                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        notificationManager.notify(102, microNotification)
                    }, 4000)

                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        notificationManager.notify(103, promoNotification)
                    }, 6000)

                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        notificationManager.notify(104, failedNotification)
                    }, 8000)

                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        notificationManager.notify(105, greedyPromo)
                    }, 10000)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Kirim Notifikasi Tes (Lengkap)", color = Primary)
            }
        }
    }
}

@Composable
fun NotificationSwitchItem(
    title: String,
    description: String,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title, 
                color = if (enabled) TextPrimary else TextPrimary.copy(alpha = 0.5f), 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description, 
                color = if (enabled) TextSecondary else TextSecondary.copy(alpha = 0.5f), 
                style = MaterialTheme.typography.bodySmall
            )
        }
        Switch(
            checked = checked, 
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary,
                uncheckedThumbColor = TextSecondary,
                uncheckedTrackColor = Surface,
                disabledCheckedTrackColor = Primary.copy(alpha = 0.5f)
            )
        )
    }
}
