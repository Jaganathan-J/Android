package com.example.dynamictheme.domain.usecase

import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.repository.IconRepository
import com.example.dynamictheme.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateIconStyleUseCase @Inject constructor(
    private val iconRepository: IconRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?) {
        iconRepository.setIconStyle(style, weight, grade, opticalSize)
        val prefs = preferencesRepository.observeThemePreferences().first()
        preferencesRepository.updateThemePreferences(
            prefs.copy(
                selectedIconStyle = style,
                iconWeight = weight,
                iconGrade = grade,
                iconOpticalSize = opticalSize
            )
        )
    }
}