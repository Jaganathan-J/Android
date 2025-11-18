package com.example.dynamicmaterialthemesync.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dynamicmaterialthemesync.domain.model.ThemeMode

@Composable
fun HomeRoute(
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        state = state,
        onSyncTheme = viewModel::onSyncThemeClicked,
        onThemeModeChanged = viewModel::onThemeModeChanged,
        onUseDynamicColorChanged = viewModel::onUseDynamicColorChanged,
        onUseRemoteThemeChanged = viewModel::onUseRemoteThemeChanged,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToProfile = onNavigateToProfile
    )
}

@Composable
fun HomeScreen(
    state: HomeViewModelState,
    onSyncTheme: () -> Unit,
    onThemeModeChanged: (ThemeMode) -> Unit,
    onUseDynamicColorChanged: (Boolean) -> Unit,
    onUseRemoteThemeChanged: (Boolean) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Dynamic Theme") },
            navigationIcon = {
                IconButton(onClick = onNavigateToProfile) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = onNavigateToSettings) {
                    Icon(imageVector = Icons.Default.Brightness4, contentDescription = null)
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Material 3 Dynamic Theme Sync", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Mode: ${state.preferences.themeMode}")
            Spacer(modifier = Modifier.height(16.dp))

            SegmentedButtonRow {
                SegmentedButton(
                    selected = state.preferences.themeMode == ThemeMode.SYSTEM,
                    onClick = { onThemeModeChanged(ThemeMode.SYSTEM) }
                ) { Text("System") }
                SegmentedButton(
                    selected = state.preferences.themeMode == ThemeMode.LIGHT,
                    onClick = { onThemeModeChanged(ThemeMode.LIGHT) }
                ) { Text("Light") }
                SegmentedButton(
                    selected = state.preferences.themeMode == ThemeMode.DARK,
                    onClick = { onThemeModeChanged(ThemeMode.DARK) }
                ) { Text("Dark") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onSyncTheme) {
                Icon(imageVector = Icons.Default.CloudDownload, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Sync Theme")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Dynamic color: ${state.preferences.useDynamicColor}")
            Text(text = "Remote theme: ${state.preferences.useRemoteTheme}")

            when (val uiState = state.uiState) {
                HomeUiState.Idle -> {}
                HomeUiState.Loading -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Syncing theme...")
                }
                is HomeUiState.Error -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Error: ${uiState.message}", color = MaterialTheme.colorScheme.error)
                }
                HomeUiState.Success -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Theme synced successfully")
                }
            }
        }
    }
}