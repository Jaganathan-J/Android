package com.example.material3showcase.domain.usecase

import com.example.material3showcase.domain.model.ButtonConfig
import com.example.material3showcase.domain.repository.ConfigRepository
import javax.inject.Inject

class SyncConfigUseCase @Inject constructor(
    private val repository: ConfigRepository
) {
    suspend operator fun invoke(): Result<List<ButtonConfig>> {
        return repository.fetchButtonConfigs()
    }
}
