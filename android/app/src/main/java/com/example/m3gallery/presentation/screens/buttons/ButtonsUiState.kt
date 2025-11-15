package com.example.m3gallery.presentation.screens.buttons

import com.example.m3gallery.domain.model.ComponentItem

data class ButtonsUiState(
    val isLoading: Boolean = false,
    val isPrimaryButtonLoading: Boolean = false,
    val isEnabled: Boolean = true,
    val errorMessage: String? = null,
    val items: List<ComponentItem> = emptyList()
)
