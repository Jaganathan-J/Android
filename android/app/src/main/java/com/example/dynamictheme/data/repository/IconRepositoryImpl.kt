package com.example.dynamictheme.data.repository

import com.example.dynamictheme.domain.model.IconStyleConfig
import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.repository.IconRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IconRepositoryImpl @Inject constructor() : IconRepository {

    private var config: IconStyleConfig = IconStyleConfig(
        style = IconStyle.Filled,
        weight = 400,
        grade = 0,
        opticalSize = 24
    )

    override suspend fun getIconStyle(): IconStyleConfig = config

    override suspend fun setIconStyle(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?) {
        config = IconStyleConfig(
            style = style,
            weight = weight ?: 400,
            grade = grade ?: 0,
            opticalSize = opticalSize ?: 24
        )
    }

    override fun supportedStyles(): List<IconStyle> = listOf(
        IconStyle.Filled,
        IconStyle.Outlined,
        IconStyle.Rounded
    )
}