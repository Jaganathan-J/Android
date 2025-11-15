package com.example.dynamicmaterialthemesync.presentation.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dynamicmaterialthemesync.domain.model.FontOption
import com.example.dynamicmaterialthemesync.presentation.ThemeIntent
import com.example.dynamicmaterialthemesync.presentation.ThemeViewModel

@Composable
fun TypographyPickerHost(onBack: () -> Unit) {
    val themeViewModel: ThemeViewModel = hiltViewModel()
    TypographyPickerScreen(
        onFontSelected = { family ->
            themeViewModel.onIntent(ThemeIntent.SelectTypography(family))
            onBack()
        },
        onBack = onBack,
        availableFonts = emptyList()
    )
}

@Composable
fun TypographyPickerScreen(
    onFontSelected: (String) -> Unit,
    onBack: () -> Unit,
    availableFonts: List<FontOption>
) {
    val selected = remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Typography") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Choose font family", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(availableFonts) { font ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selected.value = font.name
                                onFontSelected(font.name)
                            }
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = font.name, style = MaterialTheme.typography.bodyLarge)
                        Text(text = font.source, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Cancel")
            }
        }
    }
}