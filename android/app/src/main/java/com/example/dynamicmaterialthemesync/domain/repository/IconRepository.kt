package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.IconAxes
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import kotlinx.coroutines.flow.Flow

interface IconRepository {
    fun getIconStyle(): Flow<IconStyleConfig>
    suspend fun setIconStyle(style: IconStyle, axes: IconAxes)
    fun supportedStyles(): List<IconStyle>
}