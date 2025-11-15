package com.example.dynamicmaterialthemesync.presentation.theme

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dynamicmaterialthemesync.R
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ThemeSettingsScreen(
    uiStateFlow: StateFlow<ThemeUiState>,
    onIntent: (ThemeIntent) -> Unit,
    navigateToTypographyPicker: () -> Unit,
    navigateToIconPicker: () -> Unit,
    navigateToThemeSourceSelector: () -> Unit
) {
    val uiState by uiStateFlow.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.status) {
        when (val status = uiState.status) {
            is ThemeStatus.Error -> {
                snackbarHostState.showSnackbar(status.message)
            }
            ThemeStatus.Synced -> {
                snackbarHostState.showSnackbar(stringResource(id = R.string.snackbar_sync_success))
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.theme))
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Crossfade(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            targetState = uiState.themeModel
        ) { _ ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ThemeSourceSection(uiState = uiState, onIntent = onIntent, navigateToThemeSourceSelector = navigateToThemeSourceSelector)
                ColorSection(uiState = uiState, onIntent = onIntent)
                TypographySection(uiState = uiState, navigateToTypographyPicker = navigateToTypographyPicker)
                IconsSection(uiState = uiState, navigateToIconPicker = navigateToIconPicker)
                PersistenceSection(uiState = uiState, onIntent = onIntent)
            }
        }
    }
}

@Composable
private fun ThemeSourceSection(
    uiState: ThemeUiState,
    onIntent: (ThemeIntent) -> Unit,
    navigateToThemeSourceSelector: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Palette, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = stringResource(id = R.string.theme_source))
                    Text(
                        text = when (uiState.themeSource.type) {
                            ThemeSourceType.SYSTEM_DYNAMIC -> stringResource(id = R.string.theme_source_system_dynamic)
                            ThemeSourceType.REMOTE -> stringResource(id = R.string.theme_source_remote)
                            ThemeSourceType.LOCAL -> stringResource(id = R.string.theme_source_local)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Button(onClick = { onIntent(ThemeIntent.RequestSync) }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(id = R.string.sync_now))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = navigateToThemeSourceSelector) {
            Text(text = "Change source")
        }
    }
}

@Composable
private fun ColorSection(
    uiState: ThemeUiState,
    onIntent: (ThemeIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Outlined.Brightness4, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.use_dynamic_color))
            }
            Switch(
                checked = uiState.useDynamicColor,
                onCheckedChange = { checked ->
                    onIntent(ThemeIntent.ToggleDynamicColor(checked))
                },
                enabled = uiState.supportsDynamicColor
            )
        }
    }
}

@Composable
private fun TypographySection(
    uiState: ThemeUiState,
    navigateToTypographyPicker: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.TextFields, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = stringResource(id = R.string.typography))
                    Text(
                        text = uiState.themeModel.typography.bodyMedium.fontFamily?.toString() ?: "System",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            IconButton(onClick = navigateToTypographyPicker) {
                Icon(imageVector = Icons.Default.TextFields, contentDescription = null)
            }
        }
    }
}

@Composable
private fun IconsSection(
    uiState: ThemeUiState,
    navigateToIconPicker: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.icons))
            Button(onClick = navigateToIconPicker) {
                Text(text = stringResource(id = R.string.icon_style))
            }
        }
    }
}

@Composable
private fun PersistenceSection(
    uiState: ThemeUiState,
    onIntent: (ThemeIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.apply_on_launch))
            // This demo uses DataStore apply flag implicitly; could be wired to a dedicated use case
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { onIntent(ThemeIntent.ApplyChanges) }, enabled = uiState.pendingChanges) {
                Text(text = stringResource(id = R.string.apply))
            }
            Button(onClick = { onIntent(ThemeIntent.ResetDefaults) }) {
                Text(text = stringResource(id = R.string.reset_to_default))
            }
        }
    }
}