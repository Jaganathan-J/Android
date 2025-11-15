package com.example.dynamicmaterialthemesync.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel

val LocalAppThemeModel = staticCompositionLocalOf<ThemeModel> {
    error("ThemeModel not provided")
}

@Composable
fun DynamicTheme(
    isDarkTheme: Boolean,
    themeModel: ThemeModel,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) themeModel.colorSchemeDark else themeModel.colorSchemeLight

    CompositionLocalProvider(LocalAppThemeModel provides themeModel) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = themeModel.typography,
            shapes = themeModel.shapes,
            content = content
        )
    }
}