package com.example.m3gallery.domain.usecase

import com.example.m3gallery.domain.models.SnackMessage

class TriggerSnackbarUseCase {
    operator fun invoke(message: String, actionLabel: String? = null): SnackMessage {
        return SnackMessage(message = message, actionLabel = actionLabel)
    }
}
