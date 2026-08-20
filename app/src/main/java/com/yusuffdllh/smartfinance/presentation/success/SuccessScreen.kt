package com.yusuffdllh.smartfinance.presentation.success

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.components.PrimaryButton
import com.yusuffdllh.smartfinance.navigation.Screen
import com.yusuffdllh.smartfinance.ui.theme.Background
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun SuccessScreen(
    navController: NavController,
    message: String = "Transaksi Berhasil Disimpan"
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success_animation))
    // Play the check animation once, then hold on the final frame.
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
        speed = 1.1f
    )

    var visible by remember { mutableStateOf(false) }

    // Bouncy scale-in for the animation container.
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.6f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "successScale"
    )

    fun goHome() {
        navController.navigate(Screen.Dashboard.route) {
            popUpTo(Screen.Dashboard.route) { inclusive = true }
        }
    }

    LaunchedEffect(Unit) {
        visible = true
        // Auto return to dashboard after the celebration.
        delay(2200)
        goHome()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(220.dp)
                .scale(scale)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 200)) +
                    slideInVertically(tween(400, delayMillis = 200)) { it / 3 }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Berhasil!",
                    color = TextPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    color = TextSecondary,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 500))
        ) {
            PrimaryButton(
                text = "Kembali ke Beranda",
                onClick = { goHome() }
            )
        }
    }
}
