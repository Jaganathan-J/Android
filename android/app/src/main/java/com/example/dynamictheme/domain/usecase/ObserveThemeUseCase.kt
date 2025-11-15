package com.example.dynamictheme.domain.usecase

import com.example.dynamictheme.domain.model.ThemeModel
import com.example.dynamictheme.domain.repository.DynamicColorProvider
import com.example.dynamictheme.domain.repository.IconRepository
import com.example.dynamictheme.domain.repository.PreferencesRepository
import com.example.dynamictheme.domain.repository.ThemeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ObserveThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val preferencesRepository: PreferencesRepository,
    private val iconRepository: IconRepository,
    private val dynamicColorProvider: DynamicColorProvider,
    private val buildMaterialThemeUseCase: BuildMaterialThemeUseCase,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<ThemeModel> {
        val prefsFlow = preferencesRepository.observeThemePreferences()
        val tokensFlow = themeRepository.getThemeTokens().filterNotNull()

        return combine(prefsFlow, tokensFlow) { prefs, tokens ->
            val iconConfig = iconRepository.getIconStyle()
            buildMaterialThemeUseCase(
                tokens = tokens,
                preferences = prefs,
                iconConfig = iconConfig,
                dynamicColorSupported = dynamicColorProvider.isSupported()
            )
        }.flowOn(ioDispatcher)
    }
}