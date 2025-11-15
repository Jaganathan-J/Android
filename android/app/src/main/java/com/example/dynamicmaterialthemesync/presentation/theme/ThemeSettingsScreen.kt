package com.example.dynamicmaterialthemesync.presentation.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType
import com.example.dynamicmaterialthemesync.presentation.theme.ThemeIntent

@Composable
fun ThemeSettingsScreen(
    uiState: ThemeUiState,
    onIntent: (ThemeIntent) -> Unit,
    onNavigateToTypographyPicker: () -> Unit,
    onNavigateToIconPicker: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Theme") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ThemeSourceSection(uiState = uiState, onIntent = onIntent)
            }
            item {
                ColorSection(uiState = uiState, onIntent = onIntent)
            }
            item {
                TypographySection(uiState = uiState, onNavigateToTypographyPicker = onNavigateToTypographyPicker)
            }
            item {
                IconSection(uiState = uiState, onNavigateToIconPicker = onNavigateToIconPicker)
            }
            item {
                PersistenceSection(uiState = uiState, onIntent = onIntent)
            }
        }
    }
}

@Composable
private fun ThemeSourceSection(uiState: ThemeUiState, onIntent: (ThemeIntent) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Theme Source", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            SourceRow(
                label = "System Dynamic",
                selected = uiState.themeSource.type == ThemeSourceType.SYSTEM_DYNAMIC
            ) {
                onIntent(ThemeIntent.SelectThemeSource(ThemeSource(ThemeSourceType.SYSTEM_DYNAMIC)))
            }
            SourceRow(
                label = "Remote JSON",
                selected = uiState.themeSource.type == ThemeSourceType.REMOTE
            ) {
                onIntent(ThemeIntent.SelectThemeSource(ThemeSource(ThemeSourceType.REMOTE)))
            }
            SourceRow(
                label = "Local JSON",
                selected = uiState.themeSource.type == ThemeSourceType.LOCAL
            ) {
                onIntent(ThemeIntent.SelectThemeSource(ThemeSource(ThemeSourceType.LOCAL)))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val statusText = when (uiState.status) {
                    ThemeStatus.Syncing -> "Syncing..."
                    ThemeStatus.Synced -> "Synced"
                    is ThemeStatus.Error -> "Error"
                    ThemeStatus.Idle -> uiState.lastSyncTime?.let { "Synced at $it" } ?: "Not synced yet"
                }
                Text(text = statusText, style = MaterialTheme.typography.bodySmall)
                Button(onClick = { onIntent(ThemeIntent.RequestSync) }) {
                    Text(text = "Sync Now")
                }
            }
        }
    }
}

@Composable
private fun SourceRow(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        if (selected) {
            Text(text = "Selected", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun ColorSection(uiState: ThemeUiState, onIntent: (ThemeIntent) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Color", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (uiState.supportsDynamicColor) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Use Dynamic Color")
                        Text(text = "Android 12+ system colors", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = uiState.useDynamicColor,
                        onCheckedChange = { onIntent(ThemeIntent.ToggleDynamicColor(it)) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Preview", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            ColorPreviewRow()
        }
    }
}

@Composable
private fun ColorPreviewRow() {
    val roles = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.error
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        roles.forEach { color ->
            ColorChip(color = color)
        }
    }
}

@Composable
private fun ColorChip(color: Color) {
    val animatedColor by animateColorAsState(targetValue = color, animationSpec = tween(200), label = "colorChip")
    Card(
        modifier = Modifier
            .height(32.dp)
            .weight(1f),
        containerColor = animatedColor
    ) {}
}

@Composable
private fun TypographySection(uiState: ThemeUiState, onNavigateToTypographyPicker: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Typography", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToTypographyPicker() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = uiState.selectedFontFamily ?: "Default", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = "From Theme JSON / Google Fonts", style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Aa", style = MaterialTheme.typography.displayLarge)
            Text(text = "Headline", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Body text sample", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun IconSection(uiState: ThemeUiState, onNavigateToIconPicker: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Icons", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToIconPicker() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Material Symbols Style")
                    Text(text = uiState.iconStyleConfig.style.name, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun PersistenceSection(uiState: ThemeUiState, onIntent: (ThemeIntent) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Persistence", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Apply on launch")
                }
                // Always true for now
                Switch(checked = true, onCheckedChange = { })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onIntent(ThemeIntent.ResetDefaults) }) {
                Text(text = "Reset to Default")
            }
        }
    }
}