package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
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
        val tokensFlow = themeRepository.getThemeTokens()
        val prefsFlow = preferencesRepository.observeThemePreferences()
        val fontFlow = fontRepository.getPreferredFont()
        val iconFlow = iconRepository.getIconStyle()
        val syncInfoFlow = themeRepository.getLastSyncInfo()

        return combine(tokensFlow, prefsFlow, fontFlow, iconFlow, syncInfoFlow) { tokens, prefs, fontConfig, iconConfig, syncInfo ->
            val supportsDynamic = dynamicColorProvider.isSupported()
            buildMaterialThemeUseCase(
                tokens = tokens,
                preferences = prefs,
                fontConfig = fontConfig,
                iconConfig = iconConfig,
                supportsDynamicColor = supportsDynamic,
                lastSyncTimeMillis = syncInfo.lastSyncTimeMillis,
                themeVersion = syncInfo.themeVersion
            )
        }
    }
}