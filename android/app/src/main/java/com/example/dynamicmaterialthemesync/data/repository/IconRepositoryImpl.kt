package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class IconRepositoryImpl : IconRepository {

    private val state = MutableStateFlow(
        IconStyleConfig(
            style = IconStyle.OUTLINED,
            weight = 400,
            grade = 0,
            opticalSize = 24
        )
    )

    override fun getIconStyle(): Flow<IconStyleConfig> = state

    override suspend fun setIconStyle(config: IconStyleConfig) {
        state.value = config
    }

    override fun supportedStyles(): List<IconStyle> = listOf(
        IconStyle.FILLED,
        IconStyle.OUTLINED,
        IconStyle.ROUNDED
    )
}