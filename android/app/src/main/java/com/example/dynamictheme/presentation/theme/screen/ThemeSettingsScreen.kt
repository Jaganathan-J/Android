package com.example.dynamictheme.presentation.theme.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.model.enums.ThemeSource
import com.example.dynamictheme.presentation.theme.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSettingsScreen(
    viewModel: ThemeViewModel,
    snackbarHost: @Composable () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Theme") })
        },
        snackbarHost = { snackbarHost() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                ThemeSourceSection(
                    selectedSource = state.themeSource,
                    supportsDynamic = state.supportsDynamicColor,
                    onSelectSource = viewModel::onSelectThemeSource,
                    onSyncClick = { viewModel.onRequestSync() },
                    lastSyncTime = state.lastSyncTime,
                    themeVersion = state.themeVersion
                )
            }
            item {
                DynamicColorSection(
                    enabled = state.useDynamicColor,
                    supportsDynamic = state.supportsDynamicColor,
                    onToggle = viewModel::onToggleDynamicColor
                )
            }
            item {
                TypographySection(onTypographyClick = { /* Navigate to typography picker */ })
            }
            item {
                IconSection(
                    iconStyle = state.iconStyle,
                    onIconStyleSelected = { style ->
                        viewModel.onSelectIconStyle(style, state.iconWeight, state.iconGrade, state.iconOpticalSize)
                    }
                )
            }
            item {
                PersistenceSection(
                    applyOnLaunch = state.applyOnLaunch,
                    onApplyOnLaunchChange = viewModel::onToggleApplyOnLaunch,
                    onResetClick = viewModel::onResetDefaults,
                    onApplyClick = viewModel::onApplyChanges,
                    pendingChanges = state.pendingChanges
                )
            }
        }
    }
}

@Composable
private fun ThemeSourceSection(
    selectedSource: ThemeSource,
    supportsDynamic: Boolean,
    onSelectSource: (ThemeSource) -> Unit,
    onSyncClick: () -> Unit,
    lastSyncTime: Long?,
    themeVersion: String?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Theme source", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        if (supportsDynamic) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioItem(
                    text = "System dynamic",
                    selected = selectedSource == ThemeSource.SystemDynamic,
                    onClick = { onSelectSource(ThemeSource.SystemDynamic) }
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioItem(
                text = "Remote JSON",
                selected = selectedSource == ThemeSource.Remote,
                onClick = { onSelectSource(ThemeSource.Remote) }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioItem(
                text = "Local JSON",
                selected = selectedSource == ThemeSource.Local,
                onClick = { onSelectSource(ThemeSource.Local) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = when {
                lastSyncTime != null -> "Synced"
                else -> "Not synced"
            },
            style = MaterialTheme.typography.bodySmall
        )
        themeVersion?.takeIf { it.isNotEmpty() }?.let {
            Text(text = "Theme version: ${'$'}it", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onSyncClick) {
            Text(text = "Sync now")
        }
    }
}

@Composable
private fun RadioItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material3.RadioButton(selected = selected, onClick = onClick)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Composable
private fun DynamicColorSection(
    enabled: Boolean,
    supportsDynamic: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Color", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        if (supportsDynamic) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Use dynamic color")
                    Text(text = "Android 12+", style = MaterialTheme.typography.bodySmall)
                }
                Switch(checked = enabled, onCheckedChange = onToggle)
            }
        } else {
            Text(
                text = "Dynamic color not supported on this device",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun TypographySection(onTypographyClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Typography", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Font family")
                Text(text = "From Theme JSON / Google Fonts", style = MaterialTheme.typography.bodySmall)
            }
            TextButton(onClick = onTypographyClick) {
                Text(text = "Change")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Crossfade(targetState = true) { _ ->
            Column {
                Text(text = "Display", style = MaterialTheme.typography.displayLarge)
                Text(text = "Title", style = MaterialTheme.typography.titleMedium)
                Text(text = "Body", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun IconSection(
    iconStyle: IconStyle,
    onIconStyleSelected: (IconStyle) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Icons", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Style: ${'$'}iconStyle", modifier = Modifier.weight(1f))
            TextButton(onClick = { onIconStyleSelected(IconStyle.Filled) }) {
                Text(text = "Filled")
            }
            TextButton(onClick = { onIconStyleSelected(IconStyle.Outlined) }) {
                Text(text = "Outlined")
            }
            TextButton(onClick = { onIconStyleSelected(IconStyle.Rounded) }) {
                Text(text = "Rounded")
            }
        }
    }
}

@Composable
private fun PersistenceSection(
    applyOnLaunch: Boolean,
    onApplyOnLaunchChange: (Boolean) -> Unit,
    onResetClick: () -> Unit,
    onApplyClick: () -> Unit,
    pendingChanges: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Persistence", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Apply on launch")
            }
            Switch(checked = applyOnLaunch, onCheckedChange = onApplyOnLaunchChange)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onApplyClick, enabled = pendingChanges) {
                Text(text = "Apply")
            }
            TextButton(onClick = onResetClick) {
                Text(text = "Reset to default")
            }
        }
    }
}