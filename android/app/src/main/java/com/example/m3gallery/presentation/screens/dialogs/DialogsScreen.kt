package com.example.m3gallery.presentation.screens.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m3gallery.presentation.components.M3ElevatedButton
import com.example.m3gallery.presentation.components.M3FilledButton
import com.example.m3gallery.presentation.components.M3OutlinedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(onBack: () -> Unit) {
    var showConfirm by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(false) }
    var showInput by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }

    if (showConfirm) {
        AlertDialog(
            onDismissRequest = { showConfirm = false },
            confirmButton = {
                TextButton(onClick = { showConfirm = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirm = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Confirmation") },
            text = { Text("Do you confirm this demo action?") }
        )
    }

    if (showWarning) {
        AlertDialog(
            onDismissRequest = { showWarning = false },
            confirmButton = {
                TextButton(onClick = { showWarning = false }) {
                    Text("Understood")
                }
            },
            title = { Text("Warning") },
            text = { Text("This is a warning style dialog sample.") }
        )
    }

    if (showInput) {
        AlertDialog(
            onDismissRequest = { showInput = false },
            confirmButton = {
                TextButton(onClick = { showInput = false }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showInput = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Input Dialog") },
            text = {
                Column {
                    Text("Enter some text to simulate form input.")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = inputText.length > 15,
                        supportingText = {
                            if (inputText.length > 15) {
                                Text("Max 15 characters")
                            }
                        }
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dialogs", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Confirmation, warning and input-based dialog samples.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        M3FilledButton(
            text = "Show Confirmation Dialog",
            modifier = Modifier.fillMaxWidth(),
            onClick = { showConfirm = true }
        )
        Spacer(modifier = Modifier.height(12.dp))
        M3OutlinedButton(
            text = "Show Warning Dialog",
            modifier = Modifier.fillMaxWidth(),
            onClick = { showWarning = true }
        )
        Spacer(modifier = Modifier.height(12.dp))
        M3ElevatedButton(
            text = "Show Input Dialog",
            modifier = Modifier.fillMaxWidth(),
            onClick = { showInput = true }
        )
        Spacer(modifier = Modifier.height(24.dp))
        M3OutlinedButton(
            text = "Back",
            modifier = Modifier.fillMaxWidth(),
            onClick = onBack
        )
    }
}
