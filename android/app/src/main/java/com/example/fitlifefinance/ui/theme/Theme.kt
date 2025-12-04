package com.example.fitlifefinance.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = White,
    secondary = GreenDark,
    onSecondary = White,
    tertiary = OrangeShopping,
    background = LightGrayBg,
    surface = White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = RedExpense
)

@Composable
fun FitLifeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // For this prototype, we stick to Light Theme as defined in the wires
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}