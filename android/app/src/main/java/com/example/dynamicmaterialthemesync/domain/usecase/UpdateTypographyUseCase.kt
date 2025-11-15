package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import javax.inject.Inject

class UpdateTypographyUseCase @Inject constructor(
    private val fontRepository: FontRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(fontFamily: String) {
        fontRepository.setPreferredFont(fontFamily)
        preferencesRepository.updateThemePreferences { prefs ->
            prefs.copy(selectedFontFamily = fontFamily)
        }
    }
}