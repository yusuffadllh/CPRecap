package com.yusuffdllh.smartfinance.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.components.PrimaryButton
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun OnboardingScreen(
    onContinue: () -> Unit = {},
    onSkip: () -> Unit = {}
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Kelola keuangan\ndengan mudah",
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Catat, pantau, dan capai tujuan\nkeuanganmu dengan CPRecap.",
            color = TextSecondary,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(R.drawable.onboarding),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row {

            repeat(4) {

                Box(

                    modifier = Modifier
                        .padding(4.dp)
                        .size(
                            if (it == 0) 12.dp else 8.dp
                        )
                        .clip(CircleShape)
                        .background(
                            if (it == 0)
                                Primary
                            else
                                Surface2
                        )

                )

            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(

            text = "Lanjutkan",

            onClick = onContinue

        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(

            onClick = onSkip

        ) {

            Text(
                "Lewati",
                color = TextSecondary
            )

        }

    }

}