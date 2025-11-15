package com.example.m3gallery.domain.usecase

import com.example.m3gallery.domain.models.SnackMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

/**
 * Simple use case that exposes a flow of one-off snackbar messages.
 */
class TriggerSnackbarUseCase @Inject constructor() {

    private val _snackbarChannel = Channel<SnackMessage>(Channel.BUFFERED)
    val snackbarFlow = _snackbarChannel.receiveAsFlow()

    suspend operator fun invoke(message: SnackMessage) {
        _snackbarChannel.send(message)
    }
}
