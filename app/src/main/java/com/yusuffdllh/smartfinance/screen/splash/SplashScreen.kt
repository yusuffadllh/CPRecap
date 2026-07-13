package com.yusuffdllh.smartfinance.screen.splash

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.components.LoadingBar
import com.yusuffdllh.smartfinance.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    var progress by remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(Unit) {

        while (progress < 1f) {

            delay(20)

            progress += 0.01f

        }

        delay(200)

        navController.navigate(Screen.Onboarding.route) {

            popUpTo(Screen.Splash.route) {

                inclusive = true

            }

        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF08111F))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(R.drawable.logo_cprecap),
                    contentDescription = "Logo",
                    modifier = Modifier.size(220.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "CPRecap",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Catat • Pantau • Rekap",
                    color = Color(0xFF94A3B8),
                    fontSize = 18.sp
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 45.dp),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Text(
                    text = "Mempersiapkan data kamu...",
                    color = Color(0xFFD1D5DB),
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                LoadingBar(progress)

            }

        }

    }

}