package com.example.material3showcase.ui.screens.dialogs

import com.example.material3showcase.domain.model.DialogModel

data class DialogsUiState(
    val isLoading: Boolean = false,
    val dialogs: List<DialogModel> = emptyList(),
    val errorMessage: String? = null,
    val activeDialog: DialogModel? = null,
    val lastAction: String? = null
)

sealed class DialogsEvent {
    data class ShowDialog(val dialog: DialogModel) : DialogsEvent()
    object Dismiss : DialogsEvent()
    data class Confirm(val dialogId: String) : DialogsEvent()
    object Retry : DialogsEvent()
}
