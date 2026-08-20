package com.yusuffdllh.smartfinance.presentation.transaction.components

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DatePickerDialog(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, day ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, day)
            onDateSelected(selectedCalendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.maxDate = System.currentTimeMillis()
        setOnDismissListener { onDismiss() }
        show()
    }
}
