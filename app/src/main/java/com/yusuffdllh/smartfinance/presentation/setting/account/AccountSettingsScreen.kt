package com.yusuffdllh.smartfinance.presentation.setting.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.data.model.User
import com.yusuffdllh.smartfinance.presentation.setting.account.components.*
import com.yusuffdllh.smartfinance.presentation.transaction.components.DatePickerDialog
import com.yusuffdllh.smartfinance.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingsScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val isEditMode by viewModel.isEditMode.collectAsState()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    
    var showDatePicker by remember { mutableStateOf(false) }
    var showGenderPicker by remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.updateProfile(name, it.toString()) }
    }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            phone = it.phone
            birthDate = it.birthDate
            gender = it.gender
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is AccountUiState.Success) {
            showSuccessAnimation = true
            // Delay is handled in the overlay or keep here for simple control
            viewModel.toggleEditMode(false)
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = {
                val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                birthDate = fmt.format(Date(it))
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showGenderPicker) {
        AlertDialog(
            onDismissRequest = { showGenderPicker = false },
            title = { Text("Pilih Jenis Kelamin") },
            text = {
                Column {
                    listOf("Laki-laki", "Perempuan").forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if(gender == option) Primary.copy(alpha = 0.1f) else Transparent)
                                .clickable {
                                    gender = option
                                    showGenderPicker = false
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = gender == option, onClick = null)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(option, color = TextPrimary)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showGenderPicker = false }) { Text("Batal") }
            },
            containerColor = Surface
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Informasi Akun", color = TextPrimary, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = TextPrimary)
                        }
                    },
                    actions = {
                        if (!isEditMode) {
                            IconButton(onClick = { viewModel.toggleEditMode(true) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Primary)
                            }
                        } else {
                            TextButton(onClick = { 
                                user?.let {
                                    viewModel.updateFullProfile(it.copy(
                                        name = name,
                                        phone = phone,
                                        birthDate = birthDate,
                                        gender = gender
                                    ))
                                }
                            }) {
                                Text("Simpan", color = Primary, fontWeight = FontWeight.Bold)
                            }
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
                user?.let { currentUser ->
                    AccountProfileCard(
                        user = currentUser,
                        onChangePhotoClick = if (isEditMode) { { photoPickerLauncher.launch("image/*") } } else { { } }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AccountInfoCard(
                        user = currentUser.copy(name = name, phone = phone, birthDate = birthDate, gender = gender),
                        isEditMode = isEditMode,
                        onNameChange = { name = it },
                        onPhoneChange = { phone = it },
                        onBirthDateClick = { showDatePicker = true },
                        onGenderClick = { showGenderPicker = true }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AccountMenuCard(
                        onDeleteAccountClick = { /* Handle delete */ }
                    )
                    
                    if (uiState is AccountUiState.Loading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Primary)
                        }
                    }
                    
                    if (uiState is AccountUiState.Error) {
                        Text(
                            text = (uiState as AccountUiState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }

        // Success Overlay with Text and Button
        if (showSuccessAnimation) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background.copy(alpha = 0.9f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.padding(32.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Primary.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(160.dp),
                            shape = CircleShape,
                            color = Primary.copy(alpha = 0.1f)
                        ) {
                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success_animation))
                            LottieAnimation(
                                composition = composition,
                                iterations = 1,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Berhasil Disimpan",
                            style = MaterialTheme.typography.headlineSmall,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Text(
                            text = "Data akun kamu telah diperbarui di cloud.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Button(
                            onClick = { 
                                showSuccessAnimation = false
                                viewModel.resetState()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary)
                        ) {
                            Text("Kembali ke Informasi Akun", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
