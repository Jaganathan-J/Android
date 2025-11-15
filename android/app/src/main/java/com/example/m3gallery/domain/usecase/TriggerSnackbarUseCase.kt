package com.example.m3gallery.domain.usecase

import com.example.m3gallery.core.Result
import com.example.m3gallery.domain.model.SnackMessage
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import javax.inject.Inject

class TriggerSnackbarUseCase @Inject constructor(
    private val repository: M3ComponentsRepository
) {
    suspend operator fun invoke(message: String): Result<SnackMessage> = repository.triggerSnackbar(message)
}
