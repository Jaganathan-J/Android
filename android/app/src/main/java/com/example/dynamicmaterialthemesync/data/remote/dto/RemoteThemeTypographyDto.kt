package com.example.dynamicmaterialthemesync.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteThemeTypographyDto(
    @Json(name = "name") val name: String,
    @Json(name = "fontFamily") val fontFamily: String?,
    @Json(name = "fontWeight") val fontWeight: Int?,
    @Json(name = "fontSize") val fontSizeSp: Float?
)