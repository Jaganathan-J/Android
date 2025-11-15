package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.domain.model.IconAxes
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class IconRepositoryImpl : IconRepository {

    private val state = MutableStateFlow(IconStyleConfig())

    override fun getIconStyle(): Flow<IconStyleConfig> = state

    override suspend fun setIconStyle(style: IconStyle, axes: IconAxes) {
        state.value = IconStyleConfig(style = style, axes = axes)
    }

    override fun supportedStyles(): List<IconStyle> = listOf(IconStyle.FILLED, IconStyle.OUTLINED, IconStyle.ROUNDED)
}