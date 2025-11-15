package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import javax.inject.Inject

class ApplyThemeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(prefs: ThemePreferences) {
        preferencesRepository.updateThemePreferences(prefs)
    }
}