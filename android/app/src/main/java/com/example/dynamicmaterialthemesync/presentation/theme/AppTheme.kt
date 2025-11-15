package com.example.dynamicmaterialthemesync.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppTheme(
    themeModel: ThemeModel,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) themeModel.colorSchemeDark else themeModel.colorSchemeLight
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(colorScheme.background, darkIcons = !useDarkTheme)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = themeModel.typography,
        shapes = themeModel.shapes,
        content = content
    )
}