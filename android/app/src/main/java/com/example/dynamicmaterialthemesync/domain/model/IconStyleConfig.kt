package com.example.dynamicmaterialthemesync.domain.model

import com.example.dynamicmaterialthemesync.domain.model.IconStyle

data class IconStyleConfig(
    val style: IconStyle = IconStyle.FILLED,
    val weight: Int? = null,
    val grade: Int? = null,
    val opticalSize: Int? = null
)