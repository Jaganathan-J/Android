package com.example.m3gallery.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FilledDemoButton(
    text: String,
    isLoading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { if (!isLoading && enabled) onClick() },
        enabled = enabled && !isLoading,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors()
    ) {
        if (isLoading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier,
                    strokeWidth = ButtonDefaults.IconSize / 8
                )
                Text(text = "Loading")
            }
        } else {
            Text(text = text)
        }
    }
}

@Composable
fun OutlinedDemoButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { if (enabled) onClick() },
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}

@Composable
fun TextDemoButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = { if (enabled) onClick() },
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}

@Composable
fun ElevatedDemoButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = { if (enabled) onClick() },
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}
