package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveThemeUseCase(
    private val themeRepository: ThemeRepository,
    private val preferencesRepository: PreferencesRepository,
    private val iconRepository: IconRepository,
    private val dynamicColorProvider: DynamicColorProvider,
    private val buildMaterialThemeUseCase: BuildMaterialThemeUseCase
) {
    operator fun invoke(): Flow<ThemeModel> {
        return combine(
            themeRepository.getThemeTokens(),
            preferencesRepository.observeThemePreferences(),
            iconRepository.getIconStyle()
        ) { tokens, prefs, iconConfig ->
            buildMaterialThemeUseCase(
                tokens = tokens,
                preferences = prefs,
                iconStyleConfig = iconConfig,
                dynamicColorProvider = dynamicColorProvider
            )
        }
    }
}