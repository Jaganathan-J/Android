package com.example.dynamictheme.domain.usecase

import com.example.dynamictheme.domain.repository.PreferencesRepository
import javax.inject.Inject

class ResetThemeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke() {
        preferencesRepository.clear()
    }
}