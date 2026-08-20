package com.yusuffdllh.smartfinance.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(20)
            progress += 0.01f
        }
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn != null) {
            delay(2200)

            if (isLoggedIn == true) {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.logo_cprecap),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "CPRecap",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Catat • Pantau • Rekap",
                fontSize = 18.sp,
                color = Color.LightGray
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 40.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Mempersiapkan data kamu...",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Primary,
                trackColor = Color(0xFF3C4354)
            )
        }
    }
}