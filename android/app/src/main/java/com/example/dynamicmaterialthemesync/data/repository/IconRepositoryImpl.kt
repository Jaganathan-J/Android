package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IconRepositoryImpl @Inject constructor() : IconRepository {

    private val iconStyleFlow = MutableStateFlow(IconStyleConfig())

    override fun getIconStyle(): Flow<IconStyleConfig> = iconStyleFlow.asStateFlow()

    override suspend fun setIconStyle(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?) {
        iconStyleFlow.value = IconStyleConfig(
            style = style,
            weight = weight,
            grade = grade,
            opticalSize = opticalSize
        )
    }

    override fun supportedStyles(): List<IconStyle> = listOf(
        IconStyle.FILLED,
        IconStyle.OUTLINED,
        IconStyle.ROUNDED
    )
}