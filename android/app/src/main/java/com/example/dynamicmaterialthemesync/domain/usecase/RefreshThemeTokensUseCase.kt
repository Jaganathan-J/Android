package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.core.util.Result
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import javax.inject.Inject

class RefreshThemeTokensUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.refreshThemeTokens()
}