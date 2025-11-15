package com.example.dynamicthemesync.presentation.theme.typography

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dynamicthemesync.presentation.theme.settings.ThemeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypographyPickerScreen(
    uiState: ThemeUiState,
    onFontSelected: (String?) -> Unit,
    onApply: () -> Unit,
    onCancel: () -> Unit
) {
    val availableFonts = listOf("Roboto Flex", "Roboto", "Noto Sans", "System")
    val selected = remember(uiState.selectedFontFamily) { mutableStateOf(uiState.selectedFontFamily ?: "System") }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Typography") },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    TextButton(onClick = onCancel) { Text(text = "Cancel") }
                },
                actions = {
                    TextButton(onClick = {
                        onFontSelected(selected.value.takeUnless { it == "System" })
                        onApply()
                    }) {
                        Text(text = "Apply")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = "Choose font family", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(availableFonts) { font ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selected.value = font }
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = font)
                        if (selected.value == font) {
                            Text(text = "Selected", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                onFontSelected(selected.value.takeUnless { it == "System" })
                onApply()
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Apply")
            }
        }
    }
}