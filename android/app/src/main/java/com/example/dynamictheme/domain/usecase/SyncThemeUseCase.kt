package com.example.dynamictheme.domain.usecase

import com.example.dynamictheme.domain.model.ThemeTokens
import com.example.dynamictheme.domain.model.enums.ThemeSource
import com.example.dynamictheme.domain.repository.ThemeRepository
import javax.inject.Inject

class SyncThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(source: ThemeSource, remoteUrl: String?): Result<ThemeTokens> {
        return themeRepository.syncThemeTokens(source, remoteUrl)
    }
}