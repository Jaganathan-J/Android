package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import com.example.dynamicmaterialthemesync.domain.model.FontSource
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import javax.inject.Inject

class UpdateTypographyUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val fontRepository: FontRepository
) {
    suspend operator fun invoke(fontFamily: String) {
        val resolved = fontRepository.resolveFontFromTheme(fontFamily, emptyList()).getOrElse {
            FontConfig(family = fontFamily, source = FontSource.LOCAL_FALLBACK)
        }
        val current = preferencesRepository.observeThemePreferences().kotlinx.coroutines.flow.first()
        preferencesRepository.updateThemePreferences(current.copy(selectedFontFamily = resolved.family))
    }
}