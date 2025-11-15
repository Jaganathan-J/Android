package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import javax.inject.Inject

class UpdateIconStyleUseCase @Inject constructor(
    private val iconRepository: IconRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?) {
        iconRepository.setIconStyle(style, weight, grade, opticalSize)
        preferencesRepository.updateThemePreferences { prefs ->
            prefs.copy(
                selectedIconStyle = style,
                iconWeight = weight,
                iconGrade = grade,
                iconOpticalSize = opticalSize
            )
        }
    }
}