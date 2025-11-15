package com.example.material3showcase.ui.screens.textfields

import com.example.material3showcase.domain.model.TextFieldConfig

data class TextFieldsUiState(
    val isLoading: Boolean = false,
    val fields: List<TextFieldConfig> = emptyList(),
    val values: Map<String, String> = emptyMap(),
    val errorForField: Map<String, String?> = emptyMap(),
    val globalError: String? = null
)

sealed class TextFieldsEvent {
    data class OnValueChange(val id: String, val value: String) : TextFieldsEvent()
    object Retry : TextFieldsEvent()
}
