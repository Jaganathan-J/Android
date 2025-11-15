package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import kotlinx.coroutines.flow.Flow

interface IconRepository {
    fun getIconStyle(): Flow<IconStyleConfig>
    suspend fun setIconStyle(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?)
    fun supportedStyles(): List<IconStyle>
}