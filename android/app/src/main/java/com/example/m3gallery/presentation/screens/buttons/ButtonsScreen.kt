package com.example.m3gallery.presentation.screens.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m3gallery.core.UiEvent
import com.example.m3gallery.presentation.components.ButtonVariants
import kotlinx.coroutines.flow.collect

@Composable
fun ButtonsScreenRoot(innerPadding: PaddingValues) {
    val viewModel: ButtonsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }

                UiEvent.NavigateBack -> Unit
            }
        }
    }

    ButtonsScreen(
        innerPadding = innerPadding,
        state = uiState,
        onPrimaryClick = viewModel::onPrimaryButtonClick,
        onToggleEnabled = viewModel::onToggleEnabled
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(
    innerPadding: PaddingValues,
    state: ButtonUiState,
    onPrimaryClick: () -> Unit,
    onToggleEnabled: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Text(text = "Buttons Demo")
        Text(text = "Showcases Filled, Elevated, Outlined, and Text buttons with loading and disabled states.")

        ButtonVariants(
            isLoading = state.isLoading,
            isEnabled = state.isEnabled,
            onPrimaryClick = onPrimaryClick,
            onElevatedClick = {},
            onOutlinedClick = {},
            onTextClick = {}
        )

        Text(text = "Enable / Disable")
        Switch(checked = state.isEnabled, onCheckedChange = { onToggleEnabled() })

        if (state.error != null) {
            Text(text = "Error: ${state.error}")
        }

        AssistChip(onClick = onPrimaryClick, label = { Text(text = "Retry primary action") })
    }
}
