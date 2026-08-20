package com.yusuffdllh.smartfinance.utils

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DoubleBackToExit() {
    val context = LocalContext.current
    var backPressedOnce by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    BackHandler {
        if (backPressedOnce) {
            // Exit app
            (context as? android.app.Activity)?.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
            scope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }
}
