package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import javax.inject.Inject

class ToggleDynamicColorUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        preferencesRepository.updateThemePreferences { prefs ->
            prefs.copy(useDynamicColor = enabled)
        }
    }
}