package com.example.m3gallery.presentation.screens.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen() {
    val showConfirmDialog = remember { mutableStateOf(false) }
    val showWarningDialog = remember { mutableStateOf(false) }
    val showInputDialog = remember { mutableStateOf(false) }
    val inputText = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dialogs Demo") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Button(
                onClick = { showConfirmDialog.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmation Dialog")
            }
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { showWarningDialog.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Warning Dialog")
            }
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { showInputDialog.value = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Input Dialog")
            }
        }

        if (showConfirmDialog.value) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog.value = false },
                title = { Text("Delete item?") },
                text = { Text("This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = { showConfirmDialog.value = false }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog.value = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showWarningDialog.value) {
            AlertDialog(
                onDismissRequest = { showWarningDialog.value = false },
                title = { Text("Network error") },
                text = { Text("We couldn't reach the server. Please try again.") },
                confirmButton = {
                    TextButton(onClick = { showWarningDialog.value = false }) {
                        Text("Retry")
                    }
                }
            )
        }

        if (showInputDialog.value) {
            AlertDialog(
                onDismissRequest = { showInputDialog.value = false },
                title = { Text("Enter name") },
                text = {
                    Column {
                        Text("This simulates a form-like dialog.")
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = inputText.value,
                            onValueChange = { inputText.value = it },
                            label = { Text("Name") },
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showInputDialog.value = false }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showInputDialog.value = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
