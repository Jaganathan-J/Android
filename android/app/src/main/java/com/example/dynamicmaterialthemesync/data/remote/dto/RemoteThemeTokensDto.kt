package com.example.dynamicmaterialthemesync.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Mirrors the JSON exported from Figma Theme Builder (simplified).
 */
@JsonClass(generateAdapter = true)
data class RemoteThemeTokensDto(
    @Json(name = "colors") val colors: List<RemoteThemeColorDto>?,
    @Json(name = "typography") val typography: List<RemoteThemeTypographyDto>?,
    @Json(name = "shapes") val shapes: List<RemoteThemeShapeDto>?,
    @Json(name = "elevation") val elevation: List<RemoteThemeElevationDto>?,
    @Json(name = "version") val version: String?
)