package com.example.material3showcase.data.remote.dto

import com.example.material3showcase.domain.model.ButtonConfig
import com.example.material3showcase.domain.model.ButtonStyle
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ButtonConfigDto(
    @Json(name = "label") val label: String?,
    @Json(name = "style") val style: String?,
    @Json(name = "enabled") val enabled: Boolean?,
    @Json(name = "icon") val icon: String?
)

fun ButtonConfigDto.toDomain(): ButtonConfig = ButtonConfig(
    label = label ?: "Untitled",
    style = ButtonStyle.fromRaw(style ?: "filled"),
    enabled = enabled ?: true,
    icon = icon
)
