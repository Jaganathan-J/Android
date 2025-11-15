package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first

class ToggleDynamicColorUseCase(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        val current = preferencesRepository.observeThemePreferences().first()
        preferencesRepository.updateThemePreferences(current.copy(useDynamicColor = enabled))
    }
}