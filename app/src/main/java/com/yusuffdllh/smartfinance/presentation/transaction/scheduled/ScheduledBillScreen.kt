package com.yusuffdllh.smartfinance.presentation.transaction.scheduled

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.yusuffdllh.smartfinance.data.local.entity.ScheduledBillEntity
import com.yusuffdllh.smartfinance.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduledBillScreen(
    navController: NavController,
    viewModel: ScheduledBillViewModel = hiltViewModel()
) {
    val bills by viewModel.bills.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddBillDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, amount, category, date, auto ->
                viewModel.addBill(name, amount, category, date, auto)
                showAddDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tagihan Terjadwal", color = TextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .padding(24.dp)
        ) {
            if (bills.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada tagihan terjadwal", color = TextSecondary)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(bills) { bill ->
                        BillItem(bill = bill, onDelete = { viewModel.deleteBill(bill) })
                    }
                }
            }
        }
    }
}

@Composable
fun BillItem(bill: ScheduledBillEntity, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = bill.name, style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
                Text(text = "Tiap tanggal ${bill.dueDate}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Rp${formatCurrency(bill.amount)}", style = MaterialTheme.typography.bodyLarge, color = Primary, fontWeight = FontWeight.SemiBold)
            }
            
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Danger)
            }
        }
    }
}

@Composable
fun AddBillDialog(onDismiss: () -> Unit, onConfirm: (String, Long, String, Int, Boolean) -> Unit) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Tagihan") }
    var dueDate by remember { mutableStateOf("1") }
    var autoPaid by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Tagihan") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Tagihan") })
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Nominal") })
                OutlinedTextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Tanggal Jatuh Tempo (1-31)") })
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = autoPaid, onCheckedChange = { autoPaid = it })
                    Text("Auto-generate transaksi", color = TextPrimary, fontSize = 14.sp)
                }
            }
        },
        confirmButton = {
            Button(onClick = { 
                val amt = amount.toLongOrNull() ?: 0L
                val date = dueDate.toIntOrNull() ?: 1
                if (name.isNotEmpty() && amt > 0) {
                    onConfirm(name, amt, category, date, autoPaid)
                }
            }) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        },
        containerColor = Surface
    )
}

private fun formatCurrency(value: Long): String {
    return "%,d".format(value).replace(',', '.')
}
