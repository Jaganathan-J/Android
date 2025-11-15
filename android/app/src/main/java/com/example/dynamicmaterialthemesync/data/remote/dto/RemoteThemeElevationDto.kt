package com.example.dynamicmaterialthemesync.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteThemeElevationDto(
    @Json(name = "level") val level: String,
    @Json(name = "dp") val dp: Float
)