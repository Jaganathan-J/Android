package com.example.dynamictheme.domain.usecase

import com.example.dynamictheme.domain.model.ThemePreferences
import com.example.dynamictheme.domain.repository.PreferencesRepository
import javax.inject.Inject

class ApplyThemeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(preferences: ThemePreferences) {
        preferencesRepository.updateThemePreferences(preferences)
    }
}