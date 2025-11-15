package com.example.dynamicthemesync.domain.usecase

import com.example.dynamicthemesync.domain.model.IconStyle
import com.example.dynamicthemesync.domain.model.ThemePreferences
import com.example.dynamicthemesync.domain.model.ThemeSourceType
import com.example.dynamicthemesync.domain.repository.IconRepository
import com.example.dynamicthemesync.domain.repository.PreferencesRepository
import com.example.dynamicthemesync.domain.repository.ThemeRepository
import javax.inject.Inject

class SyncThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(source: ThemeSourceType, remoteUrl: String?): Result<Unit> {
        return themeRepository.syncThemeTokens(source, remoteUrl).map { tokens ->
            themeRepository.cacheThemeTokens(tokens)
        }
    }
}

class ApplyThemeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(preferences: ThemePreferences) {
        preferencesRepository.updateThemePreferences(preferences)
    }
}

class ToggleDynamicColorUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(current: ThemePreferences, enabled: Boolean) {
        preferencesRepository.updateThemePreferences(current.copy(useDynamicColor = enabled))
    }
}

class UpdateTypographyUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(current: ThemePreferences, fontFamily: String?) {
        preferencesRepository.updateThemePreferences(current.copy(selectedFontFamily = fontFamily))
    }
}

class UpdateIconStyleUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val iconRepository: IconRepository
) {
    suspend operator fun invoke(
        current: ThemePreferences,
        style: IconStyle,
        weight: Int?,
        grade: Int?,
        opticalSize: Int?
    ) {
        preferencesRepository.updateThemePreferences(
            current.copy(
                selectedIconStyle = style,
                iconWeight = weight,
                iconGrade = grade,
                iconOpticalSize = opticalSize
            )
        )
        iconRepository.setIconStyle(style, weight, grade, opticalSize)
    }
}

class ResetThemeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke() {
        preferencesRepository.clear()
    }
}