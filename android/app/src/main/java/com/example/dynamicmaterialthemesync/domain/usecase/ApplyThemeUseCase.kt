package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository

class ApplyThemeUseCase(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(preferences: ThemePreferences) {
        preferencesRepository.updateThemePreferences(preferences)
    }
}