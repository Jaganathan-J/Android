package com.example.m3gallery.core

/**
 * One-off snackbar event.
 */
sealed class SnackbarEvent {
    data class Show(val message: String, val actionLabel: String? = null) : SnackbarEvent()
}
