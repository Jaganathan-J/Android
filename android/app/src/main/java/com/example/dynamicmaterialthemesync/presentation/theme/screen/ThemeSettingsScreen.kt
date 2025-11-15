package com.example.dynamicmaterialthemesync.presentation.theme.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dynamicmaterialthemesync.R
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.presentation.ThemeIntent
import com.example.dynamicmaterialthemesync.presentation.ThemeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSettingsScreen(
    uiState: ThemeUiState,
    onIntent: (ThemeIntent) -> Unit,
    onNavigateTypographyPicker: () -> Unit,
    onNavigateIconPicker: () -> Unit,
    onNavigateThemeSourceSelector: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.snackbarMessage) {
        val message = uiState.snackbarMessage
        if (message != null) {
            snackbarHostState.showSnackbar(message)
            onIntent(ThemeIntent.SnackbarShown)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.theme_title)) })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            ThemeSourceSection(uiState = uiState, onIntent = onIntent, onNavigateThemeSourceSelector = onNavigateThemeSourceSelector)

            Spacer(modifier = Modifier.height(16.dp))

            DynamicColorSection(uiState = uiState, onIntent = onIntent)

            Spacer(modifier = Modifier.height(16.dp))

            TypographySection(onNavigateTypographyPicker = onNavigateTypographyPicker)

            Spacer(modifier = Modifier.height(16.dp))

            IconSection(onNavigateIconPicker = onNavigateIconPicker)

            Spacer(modifier = Modifier.height(16.dp))

            PersistenceSection(onIntent = onIntent)
        }
    }
}

@Composable
private fun ThemeSourceSection(
    uiState: ThemeUiState,
    onIntent: (ThemeIntent) -> Unit,
    onNavigateThemeSourceSelector: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.theme_source), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = uiState.themeSource.name)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onNavigateThemeSourceSelector) {
                Text(text = "Change")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        val statusText = when (uiState.status) {
            com.example.dynamicmaterialthemesync.presentation.ThemeStatus.Syncing -> "Syncing..."
            com.example.dynamicmaterialthemesync.presentation.ThemeStatus.Synced -> "Synced"
            is com.example.dynamicmaterialthemesync.presentation.ThemeStatus.Error -> "Error"
            com.example.dynamicmaterialthemesync.presentation.ThemeStatus.Idle -> "Idle"
        }
        Text(text = statusText, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onIntent(ThemeIntent.RequestSync) }) {
            Text(text = stringResource(id = R.string.sync_now))
        }
    }
}

@Composable
private fun DynamicColorSection(
    uiState: ThemeUiState,
    onIntent: (ThemeIntent) -> Unit
) {
    if (!uiState.supportsDynamicColor) return
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(id = R.string.use_dynamic_color), style = MaterialTheme.typography.titleMedium)
        }
        Switch(
            checked = uiState.useDynamicColor,
            onCheckedChange = { onIntent(ThemeIntent.ToggleDynamicColor(it)) }
        )
    }
}

@Composable
private fun TypographySection(
    onNavigateTypographyPicker: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.typography), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateTypographyPicker) {
            Text(text = "Choose typography")
        }
    }
}

@Composable
private fun IconSection(
    onNavigateIconPicker: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.icons), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateIconPicker) {
            Text(text = "Choose icon style")
        }
    }
}

@Composable
private fun PersistenceSection(
    onIntent: (ThemeIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onIntent(ThemeIntent.ApplyChanges) }) {
            Text(text = stringResource(id = R.string.apply))
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { onIntent(ThemeIntent.ResetDefaults) }) {
            Text(text = stringResource(id = R.string.reset_to_default))
        }
    }
}