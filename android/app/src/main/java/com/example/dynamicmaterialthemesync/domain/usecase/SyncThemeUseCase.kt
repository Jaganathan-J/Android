package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository

class SyncThemeUseCase(
    private val themeRepository: ThemeRepository,
    private val validateThemeJsonUseCase: ValidateThemeJsonUseCase
) {
    suspend operator fun invoke(source: ThemeSource): Result<Unit> {
        val result = themeRepository.syncThemeTokens(source)
        return result.mapCatching { tokens ->
            validateThemeJsonUseCase(tokens)
            themeRepository.cacheThemeTokens(tokens)
        }
    }
}