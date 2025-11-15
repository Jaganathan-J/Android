package com.example.material3showcase.ui.screens.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.material3showcase.domain.model.ButtonModel

@Composable
fun ButtonsScreen(
    uiState: ButtonsUiState,
    onEvent: (ButtonsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Buttons",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Material 3 button variants with optional leading icons.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        } else {
            AnimatedVisibility(visible = uiState.errorMessage != null, enter = fadeIn(), exit = fadeOut()) {
                uiState.errorMessage?.let { msg ->
                    Text(
                        text = msg,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = { onEvent(ButtonsEvent.Retry) }) {
                        Text("Retry")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            uiState.buttons.forEach { model ->
                ButtonItem(model = model, onClick = { onEvent(ButtonsEvent.OnButtonClick(model)) })
                Spacer(modifier = Modifier.height(8.dp))
            }

            AnimatedVisibility(visible = uiState.lastClickedLabel != null, enter = fadeIn(), exit = fadeOut()) {
                uiState.lastClickedLabel?.let { label ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Last clicked: $label",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun ButtonItem(model: ButtonModel, onClick: () -> Unit) {
    val icon: (@Composable () -> Unit)? = if (model.icon != null) {
        { Icon(Icons.Default.Check, contentDescription = null) }
    } else {
        null
    }

    when (model.style.lowercase()) {
        "outlined" -> {
            OutlinedButton(
                onClick = onClick,
                enabled = model.enabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                icon?.invoke()
                if (icon != null) Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(model.label)
            }
        }
        "text" -> {
            TextButton(
                onClick = onClick,
                enabled = model.enabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                icon?.invoke()
                if (icon != null) Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(model.label)
            }
        }
        "tonal" -> {
            FilledTonalButton(
                onClick = onClick,
                enabled = model.enabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                icon?.invoke()
                if (icon != null) Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(model.label)
            }
        }
        "elevated" -> {
            ElevatedButton(
                onClick = onClick,
                enabled = model.enabled,
                modifier = Modifier.fillMaxWidth(),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp, pressedElevation = 8.dp)
            ) {
                icon?.invoke()
                if (icon != null) Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(model.label)
            }
        }
        else -> {
            Button(
                onClick = onClick,
                enabled = model.enabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                icon?.invoke()
                if (icon != null) Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(model.label)
            }
        }
    }
}
