package com.example.dynamicmaterialthemesync.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dynamicmaterialthemesync.domain.model.ThemeMode
import com.example.dynamicmaterialthemesync.presentation.home.HomeViewModel

@Composable
fun ThemeSettingsRoute(
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ThemeSettingsScreen(
        state = state,
        onBackClick = onBackClick,
        onThemeModeChanged = viewModel::onThemeModeChanged,
        onUseDynamicColorChanged = viewModel::onUseDynamicColorChanged,
        onUseRemoteThemeChanged = viewModel::onUseRemoteThemeChanged
    )
}

@Composable
fun ThemeSettingsScreen(
    state: com.example.dynamicmaterialthemesync.presentation.home.HomeViewModelState,
    onBackClick: () -> Unit,
    onThemeModeChanged: (ThemeMode) -> Unit,
    onUseDynamicColorChanged: (Boolean) -> Unit,
    onUseRemoteThemeChanged: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Theme Settings") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )

        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Appearance", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Dynamic color (Android 12+)")
            Switch(
                checked = state.preferences.useDynamicColor,
                onCheckedChange = onUseDynamicColorChanged
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Use remote theme from Figma JSON")
            Switch(
                checked = state.preferences.useRemoteTheme,
                onCheckedChange = onUseRemoteThemeChanged
            )
        }
    }
}