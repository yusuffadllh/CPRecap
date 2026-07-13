package com.yusuffdllh.smartfinance.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(

    primary = Primary,

    secondary = Secondary,

    background = Background,

    surface = Surface,

    onPrimary = TextPrimary,

    onSecondary = TextPrimary,

    onBackground = TextPrimary,

    onSurface = TextPrimary,

    error = Danger
)

@Composable
fun SmartFinanceTheme(

    darkTheme: Boolean = isSystemInDarkTheme(),

    content: @Composable () -> Unit

) {

    MaterialTheme(

        colorScheme = DarkColors,

        typography = AppTypography,

        content = content

    )

}