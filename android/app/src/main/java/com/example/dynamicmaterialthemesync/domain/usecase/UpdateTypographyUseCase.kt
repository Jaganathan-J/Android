package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first

class UpdateTypographyUseCase(
    private val fontRepository: FontRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(fontFamily: String) {
        fontRepository.setPreferredFont(FontConfig(family = fontFamily))
        val current = preferencesRepository.observeThemePreferences().first()
        preferencesRepository.updateThemePreferences(current.copy(selectedFontFamily = fontFamily))
    }
}