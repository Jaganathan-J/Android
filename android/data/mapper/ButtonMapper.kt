package com.example.material3showcase.data.mapper

import com.example.material3showcase.data.remote.dto.ButtonDto
import com.example.material3showcase.domain.model.ButtonModel

fun ButtonDto.toDomain(): ButtonModel? {
    val safeLabel = label ?: return null
    val safeStyle = style ?: "filled"
    val safeEnabled = enabled ?: true
    return ButtonModel(
        label = safeLabel,
        style = safeStyle,
        enabled = safeEnabled,
        icon = icon
    )
}
