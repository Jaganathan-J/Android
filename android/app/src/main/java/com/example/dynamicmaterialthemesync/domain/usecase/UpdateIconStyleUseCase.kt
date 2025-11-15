package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository

class UpdateIconStyleUseCase(
    private val iconRepository: IconRepository
) {
    suspend operator fun invoke(config: IconStyleConfig) {
        iconRepository.setIconStyle(config)
    }
}