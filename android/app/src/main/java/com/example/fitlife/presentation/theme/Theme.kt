package com.example.fitlife.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = DeepIndigo,
    onPrimary = White,
    secondary = CoralAccent,
    onSecondary = White,
    tertiary = LavenderLight,
    onTertiary = DeepIndigo,
    background = LightGray,
    onBackground = DarkText,
    surface = White,
    onSurface = DarkText
)

@Composable
fun FitLifeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}