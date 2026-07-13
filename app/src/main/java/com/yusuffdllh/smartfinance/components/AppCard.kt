package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.CardBackground

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Box(

        modifier = modifier
            .background(
                CardBackground,
                RoundedCornerShape(20.dp)
            )
            .padding(16.dp)

    ) {

        content()

    }

}