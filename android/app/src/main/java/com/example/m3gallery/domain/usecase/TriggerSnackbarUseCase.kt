package com.example.m3gallery.domain.usecase

import com.example.m3gallery.domain.models.SnackMessage
import javax.inject.Inject

class TriggerSnackbarUseCase @Inject constructor() {
    operator fun invoke(message: String, actionLabel: String? = null): SnackMessage {
        return SnackMessage(message = message, actionLabel = actionLabel)
    }
}
