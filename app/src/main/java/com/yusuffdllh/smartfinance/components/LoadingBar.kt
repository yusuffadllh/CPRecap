package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingBar(
    progress: Float
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(
                Color(0xFF2B3648),
                RoundedCornerShape(100.dp)
            )

    ) {

        Box(

            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(
                    Color(0xFF22C55E),
                    RoundedCornerShape(100.dp)
                )

        )

    }

}