package com.example.m3gallery.presentation.screens.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(innerPadding: PaddingValues) {
    val showConfirmDialog = remember { mutableStateOf(false) }
    val showInputDialog = remember { mutableStateOf(false) }
    val inputText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Text(text = "Dialogs Demo")

        Button(onClick = { showConfirmDialog.value = true }) {
            Text(text = "Show Confirmation Dialog")
        }

        Button(onClick = { showInputDialog.value = true }, modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Show Input Dialog")
        }

        if (showConfirmDialog.value) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog.value = false },
                confirmButton = {
                    TextButton(onClick = { showConfirmDialog.value = false }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog.value = false }) {
                        Text(text = "Cancel")
                    }
                },
                title = { Text(text = "Delete item?") },
                text = { Text(text = "This action cannot be undone.") }
            )
        }

        if (showInputDialog.value) {
            AlertDialog(
                onDismissRequest = { showInputDialog.value = false },
                confirmButton = {
                    TextButton(onClick = { showInputDialog.value = false }) {
                        Text(text = "Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showInputDialog.value = false }) {
                        Text(text = "Cancel")
                    }
                },
                title = { Text(text = "Input Dialog") },
                text = {
                    OutlinedTextField(
                        value = inputText.value,
                        onValueChange = { inputText.value = it },
                        label = { Text(text = "Your text") }
                    )
                }
            )
        }
    }
}
