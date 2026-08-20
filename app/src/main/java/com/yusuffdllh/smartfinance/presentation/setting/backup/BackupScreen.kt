package com.yusuffdllh.smartfinance.presentation.setting.backup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupScreen(
    navController: NavController,
    viewModel: BackupViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // File creation launcher
    val createDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? ->
        uri?.let {
            if (uiState is BackupUiState.ExportSuccess) {
                val json = (uiState as BackupUiState.ExportSuccess).json
                context.contentResolver.openOutputStream(it)?.use { stream: OutputStream ->
                    stream.write(json.toByteArray())
                }
                viewModel.resetState()
            }
        }
    }

    // File picker launcher
    val openDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let { viewModel.importData(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Backup & Restore", color = TextPrimary) },
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
                .padding(24.dp)
        ) {
            Text(
                text = "Cadangkan Data",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Simpan semua transaksi dan anggaran Anda ke dalam file JSON.",
                color = TextPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.exportData() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ekspor ke JSON")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Pulihkan Data",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pilih file CPRecap_Backup.json untuk memulihkan data Anda.",
                color = TextPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { openDocumentLauncher.launch(arrayOf("application/json")) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Impor dari JSON")
            }

            if (uiState is BackupUiState.Loading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }

            if (uiState is BackupUiState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = (uiState as BackupUiState.Error).message, color = MaterialTheme.colorScheme.error)
            }
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is BackupUiState.ExportSuccess) {
            createDocumentLauncher.launch("CPRecap_Backup.json")
        }
        if (uiState is BackupUiState.ImportSuccess) {
            // Show toast or snackbar
            viewModel.resetState()
        }
    }
}
