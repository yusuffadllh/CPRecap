package com.yusuffdllh.smartfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yusuffdllh.smartfinance.navigation.NavGraph
import com.yusuffdllh.smartfinance.ui.theme.SmartFinanceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartFinanceTheme {
                NavGraph()
            }
        }
    }
}