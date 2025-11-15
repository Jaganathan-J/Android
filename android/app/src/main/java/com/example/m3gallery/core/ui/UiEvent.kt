package com.example.m3gallery.core.ui

sealed class UiEvent {
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : UiEvent()
    object NavigateBack : UiEvent()
}
