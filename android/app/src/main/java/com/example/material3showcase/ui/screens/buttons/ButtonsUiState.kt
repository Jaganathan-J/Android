package com.example.material3showcase.ui.screens.buttons

import com.example.material3showcase.domain.model.ButtonModel

data class ButtonsUiState(
    val isLoading: Boolean = false,
    val buttons: List<ButtonModel> = emptyList(),
    val errorMessage: String? = null,
    val lastClickedLabel: String? = null
)

sealed class ButtonsEvent {
    data class OnButtonClick(val model: ButtonModel) : ButtonsEvent()
    object Retry : ButtonsEvent()
}
