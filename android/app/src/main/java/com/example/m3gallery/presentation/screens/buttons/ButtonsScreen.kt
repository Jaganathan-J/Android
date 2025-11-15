package com.example.m3gallery.presentation.screens.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m3gallery.core.UiEvent
import com.example.m3gallery.presentation.components.ElevatedDemoButton
import com.example.m3gallery.presentation.components.FilledDemoButton
import com.example.m3gallery.presentation.components.OutlinedDemoButton
import com.example.m3gallery.presentation.components.TextDemoButton
import kotlinx.coroutines.flow.collect

@Composable
fun ButtonsScreen(
    innerPadding: PaddingValues,
    viewModel: ButtonsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState: SnackbarHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }
                UiEvent.DismissSnackbar -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                }
            }
        }
    }

    androidx.compose.material3.Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(scaffoldPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Buttons",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Material 3 buttons with various states.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item {
                FilledDemoButton(
                    text = "Primary",
                    isLoading = state.isPrimaryButtonLoading,
                    enabled = state.isEnabled,
                    onClick = { viewModel.onPrimaryButtonClick() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedDemoButton(
                    text = "Outlined",
                    enabled = state.isEnabled,
                    onClick = { viewModel.onToggleEnabled() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                TextDemoButton(
                    text = if (state.isEnabled) "Disable buttons" else "Enable buttons",
                    enabled = true,
                    onClick = { viewModel.onToggleEnabled() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                ElevatedDemoButton(
                    text = "Elevated",
                    enabled = state.isEnabled,
                    onClick = { viewModel.onPrimaryButtonClick() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Divider() }

            if (state.items.isNotEmpty()) {
                items(state.items) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            if (state.errorMessage != null) {
                item {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
