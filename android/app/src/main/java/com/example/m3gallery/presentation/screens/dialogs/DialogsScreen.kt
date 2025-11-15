package com.example.m3gallery.presentation.screens.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m3gallery.core.UiEvent
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(
    innerPadding: PaddingValues,
    viewModel: DialogsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState: SnackbarHostState = rememberSnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                UiEvent.DismissSnackbar -> snackbarHostState.currentSnackbarData?.dismiss()
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Dialogs",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Confirmation, warning, and input dialogs.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item {
                Button(
                    onClick = { viewModel.onShowConfirmation() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show confirmation dialog")
                }
            }

            item {
                Button(
                    onClick = { viewModel.onShowWarning() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show warning dialog")
                }
            }

            item {
                Button(
                    onClick = { viewModel.onShowInput() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show input dialog")
                }
            }

            if (state.items.isNotEmpty()) {
                item {
                    Text(
                        text = "Dialog demos",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                items(state.items) { item ->
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        if (state.showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.onConfirmationResult(false) },
                title = { Text("Confirm action") },
                text = { Text("Do you want to perform this action?") },
                confirmButton = {
                    TextButton(onClick = { viewModel.onConfirmationResult(true) }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.onConfirmationResult(false) }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (state.showWarningDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.onWarningDismiss() },
                title = { Text("Warning") },
                text = { Text("This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = { viewModel.onWarningDismiss() }) {
                        Text("Understood")
                    }
                }
            )
        }

        if (state.showInputDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.onInputConfirm() },
                title = { Text("Input dialog") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Enter some text:")
                        OutlinedTextField(
                            value = state.inputValue,
                            onValueChange = { viewModel.onInputChange(it) }
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.onInputConfirm() }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
