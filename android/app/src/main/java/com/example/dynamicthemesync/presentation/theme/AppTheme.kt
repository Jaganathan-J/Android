package com.example.dynamicthemesync.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.dynamicthemesync.domain.model.ThemeModel

val LocalThemeModel = staticCompositionLocalOf { ThemeModel.default() }

@Composable
fun AppTheme(
    themeModel: ThemeModel,
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (darkTheme) themeModel.colorSchemeDark else themeModel.colorSchemeLight

    CompositionLocalProvider(LocalThemeModel provides themeModel) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = themeModel.typography,
            shapes = themeModel.shapes,
            content = content
        )
    }
}