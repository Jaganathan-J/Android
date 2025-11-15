package com.example.dynamictheme.domain.repository

import com.example.dynamictheme.domain.model.IconStyleConfig
import com.example.dynamictheme.domain.model.enums.IconStyle

interface IconRepository {
    suspend fun getIconStyle(): IconStyleConfig
    suspend fun setIconStyle(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?)
    fun supportedStyles(): List<IconStyle>
}