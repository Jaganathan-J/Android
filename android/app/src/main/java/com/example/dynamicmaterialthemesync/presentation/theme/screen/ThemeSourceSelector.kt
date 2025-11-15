package com.example.dynamicmaterialthemesync.presentation.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.presentation.ThemeIntent
import com.example.dynamicmaterialthemesync.presentation.ThemeViewModel

@Composable
fun ThemeSourceSelectorHost(onBack: () -> Unit) {
    val viewModel: ThemeViewModel = hiltViewModel()
    ThemeSourceSelectorScreen(
        onSourceSelected = { source ->
            viewModel.onIntent(ThemeIntent.SelectThemeSource(source))
            onBack()
        },
        onBack = onBack
    )
}

@Composable
fun ThemeSourceSelectorScreen(
    onSourceSelected: (ThemeSource) -> Unit,
    onBack: () -> Unit
) {
    val urlState = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Theme source") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Select source", style = MaterialTheme.typography.titleMedium)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSourceSelected(ThemeSource.SYSTEM_DYNAMIC) }
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "System dynamic")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSourceSelected(ThemeSource.LOCAL) }
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Local JSON")
            }
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Text(text = "Remote JSON")
                OutlinedTextField(
                    value = urlState.value,
                    onValueChange = { urlState.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "URL") }
                )
            }
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Close")
            }
        }
    }
}