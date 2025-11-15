package com.example.dynamictheme.domain.usecase

import com.example.dynamictheme.domain.model.FontConfig
import com.example.dynamictheme.domain.repository.FontRepository
import com.example.dynamictheme.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateTypographyUseCase @Inject constructor(
    private val fontRepository: FontRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(fontFamily: String) {
        val metadataResult = fontRepository.fetchFontMetadata(fontFamily)
        val config = metadataResult.getOrElse { FontConfig(family = fontFamily) }
        fontRepository.setPreferredFont(config)
        fontRepository.cacheFontFiles(config)
        val prefs = preferencesRepository.observeThemePreferences().first()
        preferencesRepository.updateThemePreferences(prefs.copy(selectedFontFamily = fontFamily))
    }
}