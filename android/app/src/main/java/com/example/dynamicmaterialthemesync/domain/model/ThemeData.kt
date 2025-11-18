package com.example.dynamicmaterialthemesync.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThemeData(
    val colors: ColorTokens? = null,
    val typography: TypographyTokens? = null,
    val shapes: ShapeTokens? = null
)

@Serializable
data class ColorTokens(
    val primary: String? = null,
    @SerialName("onPrimary") val onPrimary: String? = null,
    val secondary: String? = null,
    val background: String? = null,
    val surface: String? = null,
    @SerialName("primaryContainer") val primaryContainer: String? = null,
    @SerialName("onBackground") val onBackground: String? = null
)

@Serializable
data class TypographyTokens(
    val h1: TextStyleToken? = null,
    val body1: TextStyleToken? = null
)

@Serializable
data class TextStyleToken(
    val fontFamily: String? = null,
    val fontSize: String? = null,
    val fontWeight: Int? = null
)

@Serializable
data class ShapeTokens(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null
)