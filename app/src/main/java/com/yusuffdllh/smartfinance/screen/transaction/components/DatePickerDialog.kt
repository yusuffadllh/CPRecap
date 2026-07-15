package com.yusuffdllh.smartfinance.screen.transaction.components

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DatePickerDialog(

    onDateSelected: (String) -> Unit,

    onDismiss: () -> Unit = {}

) {

    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    DatePickerDialog(

        context,

        { _, year, month, day ->

            val selectedCalendar = Calendar.getInstance()

            selectedCalendar.set(

                year,
                month,
                day

            )

            val formatter = SimpleDateFormat(

                "dd MMMM yyyy",

                Locale("id", "ID")

            )

            onDateSelected(

                formatter.format(

                    selectedCalendar.time

                )

            )

        },

        calendar.get(Calendar.YEAR),

        calendar.get(Calendar.MONTH),

        calendar.get(Calendar.DAY_OF_MONTH)

    ).apply {

        setOnDismissListener {

            onDismiss()

        }

        show()

    }

}