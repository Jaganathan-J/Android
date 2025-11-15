package com.example.m3gallery.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun M3ButtonsDemo(
    isLoading: Boolean,
    isEnabled: Boolean,
    onPrimaryClick: () -> Unit,
    onErrorClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = onPrimaryClick,
            enabled = isEnabled && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(4.dp))
                    Text(text = "Loading...")
                }
            } else {
                Text(text = "Filled Button")
            }
        }

        ElevatedButton(
            onClick = {},
            enabled = isEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Elevated Button")
        }

        OutlinedButton(
            onClick = {},
            enabled = isEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Outlined Button")
        }

        TextButton(
            onClick = {},
            enabled = isEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Text Button")
        }

        OutlinedButton(
            onClick = onErrorClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simulate Error Snackbar")
        }
    }
}
