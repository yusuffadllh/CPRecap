package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.ui.theme.Surface
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPickerBottomSheet(
    onDismiss: () -> Unit,
    onMonthSelected: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val months = remember {
        val list = mutableListOf<String>()
        val cal = Calendar.getInstance()
        val fmt = SimpleDateFormat("MMMM yyyy", Locale("id", "ID"))
        for (i in 0 until 12) {
            list.add(fmt.format(cal.time))
            cal.add(Calendar.MONTH, -1)
        }
        list
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Background
    ) {
        Text(
            text = "Pilih Bulan",
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(months) { monthStr ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onMonthSelected(monthStr) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Surface)
                ) {
                    Text(
                        text = monthStr,
                        modifier = Modifier.padding(20.dp),
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}
