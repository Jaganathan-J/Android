package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import javax.inject.Inject

class SyncThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val validateThemeJsonUseCase: ValidateThemeJsonUseCase
) {
    suspend operator fun invoke(preferences: ThemePreferences): Result<ThemeTokens> {
        val result = themeRepository.syncThemeTokens(preferences)
        return result.mapCatching { tokens ->
            validateThemeJsonUseCase(tokens)
            themeRepository.cacheThemeTokens(tokens)
            tokens
        }
    }
}