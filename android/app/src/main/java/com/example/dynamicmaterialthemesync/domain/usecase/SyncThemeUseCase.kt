package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import javax.inject.Inject

class SyncThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val validateThemeJsonUseCase: ValidateThemeJsonUseCase
) {
    suspend operator fun invoke(source: ThemeSource): Result<Unit> {
        val result = themeRepository.syncThemeTokens(source)
        return result.fold(
            onSuccess = { tokens ->
                val valid = validateThemeJsonUseCase(tokens)
                if (!valid) {
                    Result.failure(IllegalArgumentException("Invalid theme schema"))
                } else {
                    Result.success(Unit)
                }
            },
            onFailure = { Result.failure(it) }
        )
    }
}