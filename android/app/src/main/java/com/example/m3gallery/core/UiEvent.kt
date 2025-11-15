package com.example.m3gallery.core

sealed class UiEvent {
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : UiEvent()
    object DismissSnackbar : UiEvent()
}
