package com.example.m3gallery.presentation.screens.buttons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m3gallery.presentation.components.M3ButtonsDemo
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(
    viewModel: ButtonsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvents.collectLatest { snackMessage ->
            val result = snackbarHostState.showSnackbar(
                message = snackMessage.message,
                actionLabel = snackMessage.actionLabel
            )
            if (result == SnackbarResult.ActionPerformed && snackMessage.actionLabel == "Retry") {
                viewModel.onPrimaryClick()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.error != null) {
                Text(text = uiState.error)
            }
            M3ButtonsDemo(
                isLoading = uiState.isLoading,
                isEnabled = uiState.isEnabled,
                onPrimaryClick = { viewModel.onPrimaryClick() },
                onErrorClick = { viewModel.onErrorClick() }
            )
        }
    }
}
