package com.example.m3gallery.presentation.screens.buttons

sealed class ButtonsUiEffect {
    data class ShowSnackbar(val message: String) : ButtonsUiEffect()
}

data class ButtonUiState(
    val isLoading: Boolean = false,
    val isEnabled: Boolean = true,
    val error: String? = null
)
