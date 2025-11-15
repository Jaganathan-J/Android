package com.example.dynamictheme.domain.model

import com.example.dynamictheme.domain.model.enums.IconStyle

data class IconStyleConfig(
    val style: IconStyle,
    val weight: Int?,
    val grade: Int?,
    val opticalSize: Int?
)