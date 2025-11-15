package com.example.dynamicmaterialthemesync.presentation.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.presentation.ThemeIntent
import com.example.dynamicmaterialthemesync.presentation.ThemeViewModel

@Composable
fun IconStylePickerHost(onBack: () -> Unit) {
    val viewModel: ThemeViewModel = hiltViewModel()
    IconStylePickerScreen(
        onStyleSelected = { style ->
            viewModel.onIntent(ThemeIntent.SelectIconStyle(style, null, null, null))
            onBack()
        },
        onBack = onBack
    )
}

@Composable
fun IconStylePickerScreen(
    onStyleSelected: (IconStyle) -> Unit,
    onBack: () -> Unit
) {
    val styles = listOf(IconStyle.FILLED, IconStyle.OUTLINED, IconStyle.ROUNDED)
    val selected = remember { mutableStateOf<IconStyle?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Icon style") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            styles.forEach { style ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selected.value = style
                            onStyleSelected(style)
                        }
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = style.name, style = MaterialTheme.typography.bodyLarge)
                }
            }
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Cancel")
            }
        }
    }
}