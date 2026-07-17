package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yusuffdllh.smartfinance.R
import com.yusuffdllh.smartfinance.ui.theme.TextPrimary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary

@Composable
fun EmptyState(

    title: String = "Belum Ada Transaksi",

    description: String = "Transaksi yang kamu tambahkan akan muncul di sini."

) {

    val composition = rememberLottieComposition(

        LottieCompositionSpec.RawRes(

            R.raw.empty_transaction

        )

    )

    val progress = animateLottieCompositionAsState(

        composition = composition.value,

        iterations = LottieConstants.IterateForever

    )

    Column(

        modifier = Modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {

        LottieAnimation(

            composition = composition.value,

            progress = { progress.value },

            modifier = Modifier.size(240.dp)

        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(

            text = title,

            color = TextPrimary,

            fontSize = 22.sp,

            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(

            text = description,

            color = TextSecondary,

            fontSize = 15.sp,

            textAlign = TextAlign.Center

        )

    }

}