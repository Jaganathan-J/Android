package com.example.dynamicmaterialthemesync.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteThemeColorDto(
    @Json(name = "name") val name: String,
    @Json(name = "light") val light: String,
    @Json(name = "dark") val dark: String
)