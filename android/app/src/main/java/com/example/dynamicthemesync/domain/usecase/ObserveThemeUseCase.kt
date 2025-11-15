package com.example.dynamicthemesync.domain.usecase

import com.example.dynamicthemesync.domain.model.ThemeModel
import com.example.dynamicthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicthemesync.domain.repository.FontRepository
import com.example.dynamicthemesync.domain.repository.IconRepository
import com.example.dynamicthemesync.domain.repository.PreferencesRepository
import com.example.dynamicthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val preferencesRepository: PreferencesRepository,
    private val fontRepository: FontRepository,
    private val iconRepository: IconRepository,
    private val dynamicColorProvider: DynamicColorProvider,
    private val buildMaterialThemeUseCase: BuildMaterialThemeUseCase
) {
    operator fun invoke(): Flow<ThemeModel> {
        return combine(
            themeRepository.getThemeTokens(),
            preferencesRepository.observeThemePreferences(),
            fontRepository.getPreferredFont(),
            iconRepository.getIconStyle()
        ) { tokens, prefs, fontConfig, iconConfig ->
            buildMaterialThemeUseCase(tokens, prefs, fontConfig, iconConfig, dynamicColorProvider)
        }
    }
}