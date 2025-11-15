package com.example.dynamicmaterialthemesync.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteThemeShapeDto(
    @Json(name = "name") val name: String,
    @Json(name = "cornerRadius") val cornerRadius: Float?
)