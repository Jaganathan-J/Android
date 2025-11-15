package com.example.dynamicmaterialthemesync.presentation.theme

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TypographyPickerScreen(
    uiState: ThemeUiState,
    onSelectFont: (String) -> Unit,
    onApply: () -> Unit,
    onCancel: () -> Unit
) {
    val fonts = if (uiState.availableFonts.isEmpty()) listOf("Roboto", "Roboto Flex", "Noto Sans") else uiState.availableFonts
    val selected = remember(uiState.selectedFontFamily) { mutableStateOf(uiState.selectedFontFamily ?: fonts.first()) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Typography") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Select Font Family")
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(fonts) { font ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selected.value = font
                                onSelectFont(font)
                            }
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = font)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onApply, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Apply")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Cancel")
            }
        }
    }
}