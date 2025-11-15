package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import javax.inject.Inject

class ToggleDynamicColorUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        val current = preferencesRepository.observeThemePreferences().kotlinx.coroutines.flow.first()
        preferencesRepository.updateThemePreferences(current.copy(useDynamicColor = enabled))
    }
}