package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.IconAxes
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import javax.inject.Inject

class UpdateIconStyleUseCase @Inject constructor(
    private val iconRepository: IconRepository
) {
    suspend operator fun invoke(style: IconStyle, axes: IconAxes) {
        iconRepository.setIconStyle(style, axes)
    }
}