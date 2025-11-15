package com.example.dynamicthemesync.presentation.theme.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dynamicthemesync.R
import com.example.dynamicthemesync.domain.model.IconStyle
import com.example.dynamicthemesync.domain.model.ThemeSourceType

@Composable
fun ThemeSettingsScreen(
    viewModel: ThemeViewModel,
    onNavigateToTypography: () -> Unit,
    onNavigateToIconPicker: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.theme_title)) },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { ThemeSourceSection(uiState, onSourceChanged = viewModel::onSelectThemeSource, onSyncClick = viewModel::onRequestSync) }
            item { ColorSection(uiState, onToggleDynamic = viewModel::onToggleDynamicColor) }
            item { TypographySection(uiState, onNavigateToTypography) }
            item { IconSection(uiState, onNavigateToIconPicker) }
            item { PersistenceSection(uiState, onApply = viewModel::onApplyChanges, onReset = viewModel::onResetDefaults) }
            item {
                AnimatedVisibility(visible = uiState.loading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeSourceSection(
    uiState: ThemeUiState,
    onSourceChanged: (ThemeSourceType) -> Unit,
    onSyncClick: () -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(id = R.string.theme_source_title), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            SourceRow(label = stringResource(id = R.string.theme_source_system_dynamic), selected = uiState.themeSource == ThemeSourceType.SYSTEM_DYNAMIC) {
                onSourceChanged(ThemeSourceType.SYSTEM_DYNAMIC)
            }
            SourceRow(label = stringResource(id = R.string.theme_source_remote), selected = uiState.themeSource == ThemeSourceType.REMOTE) {
                onSourceChanged(ThemeSourceType.REMOTE)
            }
            SourceRow(label = stringResource(id = R.string.theme_source_local), selected = uiState.themeSource == ThemeSourceType.LOCAL) {
                onSourceChanged(ThemeSourceType.LOCAL)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = uiState.themeVersion?.let { "Version $it" } ?: stringResource(id = R.string.theme_source_status_unknown),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = onSyncClick) {
                    Text(text = stringResource(id = R.string.theme_sync_now))
                }
            }
        }
    }
}

@Composable
private fun SourceRow(label: String, selected: Boolean, onClick: () -> Unit) {
    val color by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 200),
        label = "source-row-bg"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        androidx.compose.material3.RadioButton(selected = selected, onClick = onClick)
    }
}

@Composable
private fun ColorSection(
    uiState: ThemeUiState,
    onToggleDynamic: (Boolean) -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(id = R.string.theme_color_title), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(id = R.string.theme_use_dynamic_color))
                    Text(text = stringResource(id = R.string.theme_use_dynamic_color_subtitle), style = MaterialTheme.typography.bodySmall)
                }
                Switch(
                    checked = uiState.useDynamicColor && uiState.supportsDynamicColor,
                    enabled = uiState.supportsDynamicColor,
                    onCheckedChange = onToggleDynamic
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.theme_color_preview), style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ColorChip(label = "Primary", color = MaterialTheme.colorScheme.primary, onColor = MaterialTheme.colorScheme.onPrimary)
                ColorChip(label = "Secondary", color = MaterialTheme.colorScheme.secondary, onColor = MaterialTheme.colorScheme.onSecondary)
                ColorChip(label = "Tertiary", color = MaterialTheme.colorScheme.tertiary, onColor = MaterialTheme.colorScheme.onTertiary)
            }
        }
    }
}

@Composable
private fun ColorChip(label: String, color: androidx.compose.ui.graphics.Color, onColor: androidx.compose.ui.graphics.Color) {
    val animatedColor by animateColorAsState(targetValue = color, animationSpec = tween(200), label = "color-chip-bg")
    ElevatedCard {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth(),
            ) {}
            Text(text = label, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun TypographySection(uiState: ThemeUiState, onNavigateToTypography: () -> Unit) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(id = R.string.theme_typography_title), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = uiState.selectedFontFamily ?: stringResource(id = R.string.theme_typography_default_font))
                    Text(text = stringResource(id = R.string.theme_typography_subtitle), style = MaterialTheme.typography.bodySmall)
                }
                TextButton(onClick = onNavigateToTypography) {
                    Text(text = stringResource(id = R.string.theme_change))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Aa Display", style = MaterialTheme.typography.displayLarge)
            Text(text = "Title Medium", style = MaterialTheme.typography.titleMedium)
            Text(text = "Body Medium sample text", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun IconSection(uiState: ThemeUiState, onNavigateToIconPicker: () -> Unit) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(id = R.string.theme_icons_title), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(id = R.string.theme_icons_style_label, uiState.iconStyle.name.lowercase().replaceFirstChar { it.titlecase() }))
                    Text(text = stringResource(id = R.string.theme_icons_subtitle), style = MaterialTheme.typography.bodySmall)
                }
                TextButton(onClick = onNavigateToIconPicker) {
                    Text(text = stringResource(id = R.string.theme_change))
                }
            }
        }
    }
}

@Composable
private fun PersistenceSection(uiState: ThemeUiState, onApply: () -> Unit, onReset: () -> Unit) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(id = R.string.theme_persistence_title), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(onClick = onReset) {
                    Text(text = stringResource(id = R.string.theme_reset_default))
                }
                Button(onClick = onApply, enabled = uiState.pendingChanges) {
                    Text(text = stringResource(id = R.string.theme_apply))
                }
            }
        }
    }
}