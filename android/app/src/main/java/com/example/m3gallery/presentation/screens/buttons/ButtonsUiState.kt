package com.example.m3gallery.presentation.screens.buttons

data class ButtonUiState(
    val isLoading: Boolean = false,
    val isEnabled: Boolean = true,
    val error: String? = null
)
