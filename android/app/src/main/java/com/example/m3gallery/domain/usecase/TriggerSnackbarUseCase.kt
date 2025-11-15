package com.example.m3gallery.domain.usecase

import com.example.m3gallery.domain.models.SnackMessage
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import javax.inject.Inject

class TriggerSnackbarUseCase @Inject constructor(
    private val repository: M3ComponentsRepository
) {
    suspend operator fun invoke(): SnackMessage {
        return repository.getSampleSnackMessage()
    }
}
