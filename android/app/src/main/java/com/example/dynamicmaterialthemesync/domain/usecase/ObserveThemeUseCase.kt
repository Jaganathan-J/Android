package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveThemeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val themeRepository: ThemeRepository,
    private val fontRepository: FontRepository,
    private val iconRepository: IconRepository,
    private val dynamicColorProvider: DynamicColorProvider,
    private val buildMaterialThemeUseCase: BuildMaterialThemeUseCase
) {
    operator fun invoke(): Flow<ThemeModel> {
        return combine(
            preferencesRepository.observeThemePreferences(),
            themeRepository.getThemeTokens(),
            fontRepository.getPreferredFont(),
            iconRepository.getIconStyle(),
            themeRepository.getLastSyncInfo()
        ) { prefs, tokens, fontConfig, iconConfig, syncInfo ->
            buildMaterialThemeUseCase(
                preferences = prefs,
                tokens = tokens,
                fontConfig = fontConfig,
                iconStyleConfig = iconConfig,
                dynamicColorProvider = dynamicColorProvider,
                lastSyncInfo = syncInfo
            )
        }
    }
}