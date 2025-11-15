package com.example.dynamictheme.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ThemeTokensDto(
    @Json(name = "schemaVersion") val schemaVersion: String,
    @Json(name = "themeVersion") val themeVersion: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "colors") val colors: ColorTokensDto?,
    @Json(name = "typography") val typography: TypographyTokensDto?,
    @Json(name = "shapes") val shapes: ShapeTokensDto?,
    @Json(name = "elevation") val elevation: ElevationTokensDto?,
    @Json(name = "icons") val icons: IconTokensDto?,
    @Json(name = "meta") val meta: ThemeMetaDto?
)

@JsonClass(generateAdapter = true)
data class ColorTokensDto(
    @Json(name = "light") val light: ColorSchemeTokensDto?,
    @Json(name = "dark") val dark: ColorSchemeTokensDto?
)

@JsonClass(generateAdapter = true)
data class ColorSchemeTokensDto(
    @Json(name = "primary") val primary: String?,
    @Json(name = "onPrimary") val onPrimary: String?,
    @Json(name = "primaryContainer") val primaryContainer: String?,
    @Json(name = "onPrimaryContainer") val onPrimaryContainer: String?,
    @Json(name = "secondary") val secondary: String?,
    @Json(name = "onSecondary") val onSecondary: String?,
    @Json(name = "background") val background: String?,
    @Json(name = "onBackground") val onBackground: String?,
    @Json(name = "surface") val surface: String?,
    @Json(name = "onSurface") val onSurface: String?,
    @Json(name = "surfaceVariant") val surfaceVariant: String?,
    @Json(name = "onSurfaceVariant") val onSurfaceVariant: String?,
    @Json(name = "tertiary") val tertiary: String?,
    @Json(name = "error") val error: String?,
    @Json(name = "onError") val onError: String?,
    @Json(name = "outline") val outline: String?,
    @Json(name = "inverseSurface") val inverseSurface: String?,
    @Json(name = "inverseOnSurface") val inverseOnSurface: String?
)

@JsonClass(generateAdapter = true)
data class TypographyTokensDto(
    @Json(name = "fontFamily") val fontFamily: String?,
    @Json(name = "fallback") val fallback: List<String>?,
    @Json(name = "weightMapping") val weightMapping: Map<String, Int>?,
    @Json(name = "sizeSp") val sizeSp: Map<String, Int>?,
    @Json(name = "lineHeightSp") val lineHeightSp: Map<String, Int>?,
    @Json(name = "letterSpacingEm") val letterSpacingEm: Map<String, Double>?
)

@JsonClass(generateAdapter = true)
data class ShapeTokensDto(
    @Json(name = "cornerFamily") val cornerFamily: String?,
    @Json(name = "extraSmall") val extraSmall: Int?,
    @Json(name = "small") val small: Int?,
    @Json(name = "medium") val medium: Int?,
    @Json(name = "large") val large: Int?,
    @Json(name = "extraLarge") val extraLarge: Int?
)

@JsonClass(generateAdapter = true)
data class ElevationTokensDto(
    @Json(name = "level0") val level0: Int?,
    @Json(name = "level1") val level1: Int?,
    @Json(name = "level2") val level2: Int?,
    @Json(name = "level3") val level3: Int?,
    @Json(name = "level4") val level4: Int?,
    @Json(name = "level5") val level5: Int?
)

@JsonClass(generateAdapter = true)
data class IconTokensDto(
    @Json(name = "materialSymbolsStyle") val materialSymbolsStyle: String?,
    @Json(name = "defaultWeight") val defaultWeight: Int?,
    @Json(name = "defaultGrade") val defaultGrade: Int?,
    @Json(name = "defaultOpticalSize") val defaultOpticalSize: Int?
)

@JsonClass(generateAdapter = true)
data class ThemeMetaDto(
    @Json(name = "generatedBy") val generatedBy: String?,
    @Json(name = "updatedAt") val updatedAt: String?
)