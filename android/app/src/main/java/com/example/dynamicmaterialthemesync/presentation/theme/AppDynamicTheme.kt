package com.example.dynamicmaterialthemesync.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dynamicmaterialthemesync.presentation.ThemeViewModel

@Composable
fun AppDynamicTheme(content: @Composable () -> Unit) {
    val viewModel: ThemeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val themeModel = uiState.themeModel
    MaterialTheme(
        colorScheme = themeModel.colorScheme,
        typography = themeModel.typography,
        shapes = themeModel.shapes,
        content = content
    )
}