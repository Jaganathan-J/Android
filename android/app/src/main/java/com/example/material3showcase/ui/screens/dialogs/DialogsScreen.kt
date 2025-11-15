package com.example.material3showcase.ui.screens.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.material3showcase.domain.model.DialogModel

@Composable
fun DialogsScreen(
    uiState: DialogsUiState,
    onEvent: (DialogsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Dialogs", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Alert and confirmation dialogs with actions.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.errorMessage != null -> {
                Text(text = uiState.errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { onEvent(DialogsEvent.Retry) }) {
                    Text("Retry")
                }
            }
            else -> {
                LazyColumn(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                    items(uiState.dialogs) { model ->
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onEvent(DialogsEvent.ShowDialog(model)) }
                        ) {
                            Text("Show: ${model.title}")
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = uiState.lastAction != null, enter = fadeIn(), exit = fadeOut()) {
            uiState.lastAction?.let { action ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = action, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

    AnimatedVisibility(
        visible = uiState.activeDialog != null,
        enter = scaleIn(animationSpec = spring()) + fadeIn(),
        exit = scaleOut(animationSpec = spring()) + fadeOut()
    ) {
        uiState.activeDialog?.let { dialog ->
            DialogItem(dialog = dialog, onEvent = onEvent)
        }
    }
}

@Composable
private fun DialogItem(dialog: DialogModel, onEvent: (DialogsEvent) -> Unit) {
    AlertDialog(
        onDismissRequest = { onEvent(DialogsEvent.Dismiss) },
        title = { Text(dialog.title) },
        text = { Text(dialog.message) },
        confirmButton = {
            Button(onClick = { onEvent(DialogsEvent.Confirm(dialog.id)) }) {
                Text(dialog.confirmLabel)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { onEvent(DialogsEvent.Dismiss) }) {
                Text(dialog.dismissLabel)
            }
        }
    )
}
