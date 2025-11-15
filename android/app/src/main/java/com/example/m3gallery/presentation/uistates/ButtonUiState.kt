package com.example.m3gallery.presentation.uistates

data class ButtonUiState(
    val isLoading: Boolean = false,
    val isEnabled: Boolean = true,
    val error: String? = null,
    val lastActionMessage: String? = null
)
