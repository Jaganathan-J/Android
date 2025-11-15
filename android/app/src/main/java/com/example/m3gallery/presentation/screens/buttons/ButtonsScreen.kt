package com.example.m3gallery.presentation.screens.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3gallery.presentation.components.M3ElevatedButton
import com.example.m3gallery.presentation.components.M3FilledButton
import com.example.m3gallery.presentation.components.M3OutlinedButton
import com.example.m3gallery.presentation.components.M3TextButton
import com.example.m3gallery.presentation.viewmodels.ButtonsViewModel

@Composable
fun ButtonsScreen(
    viewModel: ButtonsViewModel,
    onBack: () -> Unit,
    onShowSnackbar: (String, String?) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    if (state.error != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("Retry")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("Dismiss")
                }
            },
            title = { Text("Error") },
            text = { Text(state.error ?: "") }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Buttons", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Showcase of different Material 3 button types and states.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        M3FilledButton(
            text = if (state.isLoading) "Loading..." else "Primary Action",
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isEnabled,
            loading = state.isLoading,
            onClick = {
                viewModel.onPrimaryButtonClick()
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        M3ElevatedButton(
            text = "Toggle Enabled (Current: ${'$'}{if (state.isEnabled) "On" else "Off"})",
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.onToggleEnabled()
                onShowSnackbar(
                    message = if (state.isEnabled) "Buttons disabled" else "Buttons enabled",
                    action = "OK"
                )
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        M3OutlinedButton(
            text = "Simulate Error",
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isEnabled,
            onClick = {
                viewModel.simulateError()
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            M3TextButton(
                text = "Secondary",
                modifier = Modifier.weight(1f),
                enabled = state.isEnabled,
                onClick = {
                    onShowSnackbar("Secondary clicked", null)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            M3TextButton(
                text = "Back",
                modifier = Modifier.weight(1f),
                onClick = onBack
            )
        }

        if (!state.lastActionMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Last action: ${'$'}{state.lastActionMessage}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
