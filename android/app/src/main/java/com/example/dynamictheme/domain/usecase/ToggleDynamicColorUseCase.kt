package com.example.dynamictheme.domain.usecase

import com.example.dynamictheme.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleDynamicColorUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        val current = preferencesRepository.observeThemePreferences().first()
        preferencesRepository.updateThemePreferences(current.copy(useDynamicColor = enabled))
    }
}