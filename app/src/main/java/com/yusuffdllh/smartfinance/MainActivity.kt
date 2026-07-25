package com.yusuffdllh.smartfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import com.yusuffdllh.smartfinance.navigation.NavGraph
import com.yusuffdllh.smartfinance.ui.theme.SmartFinanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartFinanceTheme {
                Surface(color = Color(0xFF0F172A)) {
                    NavGraph()
                }
            }
        }
    }
}
