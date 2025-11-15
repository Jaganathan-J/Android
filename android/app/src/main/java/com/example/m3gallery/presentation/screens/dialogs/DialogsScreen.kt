package com.example.m3gallery.presentation.screens.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m3gallery.presentation.components.ConfirmationDialog
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(
    viewModel: DialogsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvents.collectLatest { snackMessage ->
            snackbarHostState.showSnackbar(
                message = snackMessage.message,
                actionLabel = snackMessage.actionLabel
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Dialogs Demo")
            Button(onClick = { viewModel.onShowDialogClick() }) {
                Text(text = "Show confirmation dialog")
            }
        }

        if (uiState.showConfirmation) {
            ConfirmationDialog(
                title = "Delete item?",
                message = "This action cannot be undone.",
                onConfirm = { viewModel.onDialogConfirm() },
                onDismiss = { viewModel.onDialogDismiss() }
            )
        }
    }
}
