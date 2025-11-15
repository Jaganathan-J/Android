package com.example.dynamictheme.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.dynamictheme.domain.model.ThemeModel

@Composable
fun AppTheme(
    themeModel: ThemeModel,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = themeModel.colorScheme,
        typography = themeModel.typography,
        shapes = themeModel.shapes,
        content = content
    )
}