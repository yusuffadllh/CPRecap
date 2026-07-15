package com.yusuffdllh.smartfinance.screen.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.screen.transaction.components.*
import com.yusuffdllh.smartfinance.ui.theme.Background
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale
import com.yusuffdllh.smartfinance.data.repository.DummyRepository

@Composable
fun AddTransactionScreen(

    navController: NavController

) {

    var income by remember {

        mutableStateOf(false)

    }

    var category by remember {

        mutableStateOf("Makanan")

    }

    var name by remember {

        mutableStateOf("")

    }

    var amount by remember {

        mutableStateOf("")

    }

    var date by remember {

        mutableStateOf("25 Juli 2026")

    }

    var note by remember {

        mutableStateOf("")

    }

    var showCategorySheet by remember {

        mutableStateOf(false)

    }

    var showDatePicker by remember {

        mutableStateOf(false)

    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)

    ) {

        AddTransactionHeader(

            title = if (income)
                "Tambah Pemasukan"
            else
                "Tambah Pengeluaran",

            onBackClick = {

                navController.popBackStack()

            }

        )

        Spacer(modifier = Modifier.height(28.dp))

        TransactionTypeSelector(

            selectedIncome = income,

            onTypeChanged = {

                income = it

            }

        )

        Spacer(modifier = Modifier.height(24.dp))

        CategoryDropdown(

            category = category,

            onClick = {

                showCategorySheet = true

            }

        )

        Spacer(modifier = Modifier.height(20.dp))

        NameField(

            value = name,

            onValueChange = {

                name = it

            }

        )

        Spacer(modifier = Modifier.height(20.dp))

        AmountField(

            amount = amount,

            onAmountChange = {

                amount = it

            }

        )

        Spacer(modifier = Modifier.height(20.dp))

        DateField(

            date = date,

            onClick = {

                showDatePicker = true

            }

        )

        Spacer(modifier = Modifier.height(20.dp))

        NoteField(

            note = note,

            onNoteChange = {

                note = it

            }

        )

        Spacer(modifier = Modifier.height(32.dp))

        SaveTransactionButton(

            selectedIncome = income,

            onClick = {

                if (

                    name.isBlank() ||

                    amount.isBlank()

                ) {

                    Toast.makeText(

                        navController.context,

                        "Lengkapi data terlebih dahulu",

                        Toast.LENGTH_SHORT

                    ).show()

                    return@SaveTransactionButton

                }

                DummyRepository.addTransaction(

                    title = name,

                    amount = amount,

                    income = income,

                    category = category,

                    date = date,

                    note = note

                )

                Toast.makeText(

                    navController.context,

                    "Transaksi berhasil ditambahkan",

                    Toast.LENGTH_SHORT

                ).show()

                navController.popBackStack()

            }

        )

        Spacer(modifier = Modifier.height(24.dp))

    }

    if (showCategorySheet) {

        CategoryBottomSheet(

            selectedCategory = category,

            onDismiss = {

                showCategorySheet = false

            },

            onCategorySelected = {

                category = it

                showCategorySheet = false

            }

        )

    }

    if (showDatePicker) {

        DatePickerDialog(

            onDateSelected = {

                date = it

                showDatePicker = false

            },

            onDismiss = {

                showDatePicker = false

            }

        )

    }

}