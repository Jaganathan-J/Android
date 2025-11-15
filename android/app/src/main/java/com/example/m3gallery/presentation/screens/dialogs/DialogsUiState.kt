package com.example.m3gallery.presentation.screens.dialogs

import com.example.m3gallery.domain.model.ComponentItem

data class DialogsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val items: List<ComponentItem> = emptyList(),
    val showConfirmationDialog: Boolean = false,
    val showWarningDialog: Boolean = false,
    val showInputDialog: Boolean = false,
    val inputValue: String = ""
)
