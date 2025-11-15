package com.example.m3gallery.data.remote.dto

import com.squareup.moshi.Json

data class ComponentDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String
)
