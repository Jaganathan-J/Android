package com.example.dynamicmaterialthemesync.data.mapper

import com.example.dynamicmaterialthemesync.domain.model.ColorSchemeTokens
import com.example.dynamicmaterialthemesync.domain.model.ColorTokens
import com.example.dynamicmaterialthemesync.domain.model.ElevationTokens
import com.example.dynamicmaterialthemesync.domain.model.IconTokens
import com.example.dynamicmaterialthemesync.domain.model.ShapeTokens
import com.example.dynamicmaterialthemesync.domain.model.ThemeMeta
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.model.TypographyTokens
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ThemeTokensDto(
    @Json(name = "schemaVersion") val schemaVersion: String,
    @Json(name = "themeVersion") val themeVersion: String,
    @Json(name = "name") val name: String,
    @Json(name = "colors") val colors: ColorsDto,
    @Json(name = "typography") val typography: TypographyDto?,
    @Json(name = "shapes") val shapes: ShapesDto?,
    @Json(name = "elevation") val elevation: ElevationDto?,
    @Json(name = "icons") val icons: IconsDto?,
    @Json(name = "meta") val meta: MetaDto?
)

@JsonClass(generateAdapter = true)
data class ColorsDto(
    @Json(name = "light") val light: ColorSchemeDto,
    @Json(name = "dark") val dark: ColorSchemeDto
)

@JsonClass(generateAdapter = true)
data class ColorSchemeDto(
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
data class TypographyDto(
    @Json(name = "fontFamily") val fontFamily: String?,
    @Json(name = "fallback") val fallback: List<String>?,
    @Json(name = "weightMapping") val weightMapping: Map<String, Int>?,
    @Json(name = "sizeSp") val sizeSp: Map<String, Int>?,
    @Json(name = "lineHeightSp") val lineHeightSp: Map<String, Int>?,
    @Json(name = "letterSpacingEm") val letterSpacingEm: Map<String, Double>?
)

@JsonClass(generateAdapter = true)
data class ShapesDto(
    @Json(name = "cornerFamily") val cornerFamily: String?,
    @Json(name = "extraSmall") val extraSmall: Int?,
    @Json(name = "small") val small: Int?,
    @Json(name = "medium") val medium: Int?,
    @Json(name = "large") val large: Int?,
    @Json(name = "extraLarge") val extraLarge: Int?
)

@JsonClass(generateAdapter = true)
data class ElevationDto(
    @Json(name = "level0") val level0: Int?,
    @Json(name = "level1") val level1: Int?,
    @Json(name = "level2") val level2: Int?,
    @Json(name = "level3") val level3: Int?,
    @Json(name = "level4") val level4: Int?,
    @Json(name = "level5") val level5: Int?
)

@JsonClass(generateAdapter = true)
data class IconsDto(
    @Json(name = "materialSymbolsStyle") val materialSymbolsStyle: String?,
    @Json(name = "defaultWeight") val defaultWeight: Int?,
    @Json(name = "defaultGrade") val defaultGrade: Int?,
    @Json(name = "defaultOpticalSize") val defaultOpticalSize: Int?
)

@JsonClass(generateAdapter = true)
data class MetaDto(
    @Json(name = "generatedBy") val generatedBy: String?,
    @Json(name = "updatedAt") val updatedAt: String?
)

fun ThemeTokensDto.toDomain(): ThemeTokens = ThemeTokens(
    schemaVersion = schemaVersion,
    themeVersion = themeVersion,
    name = name,
    colors = ColorTokens(
        light = light.toDomain(),
        dark = dark.toDomain()
    ),
    typography = typography?.toDomain(),
    shapes = shapes?.toDomain(),
    elevation = elevation?.toDomain(),
    icons = icons?.toDomain(),
    meta = meta?.toDomain()
)

private val ThemeTokensDto.light: ColorSchemeDto get() = colors.light
private val ThemeTokensDto.dark: ColorSchemeDto get() = colors.dark

private fun ColorSchemeDto.toDomain() = ColorSchemeTokens(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    secondary = secondary,
    onSecondary = onSecondary,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    tertiary = tertiary,
    error = error,
    onError = onError,
    outline = outline,
    inverseSurface = inverseSurface,
    inverseOnSurface = inverseOnSurface
)

private fun TypographyDto.toDomain() = TypographyTokens(
    fontFamily = fontFamily,
    fallback = fallback,
    weightMapping = weightMapping,
    sizeSp = sizeSp,
    lineHeightSp = lineHeightSp,
    letterSpacingEm = letterSpacingEm
)

private fun ShapesDto.toDomain() = ShapeTokens(
    cornerFamily = cornerFamily,
    extraSmall = extraSmall,
    small = small,
    medium = medium,
    large = large,
    extraLarge = extraLarge
)

private fun ElevationDto.toDomain() = ElevationTokens(
    level0 = level0,
    level1 = level1,
    level2 = level2,
    level3 = level3,
    level4 = level4,
    level5 = level5
)

private fun IconsDto.toDomain() = IconTokens(
    materialSymbolsStyle = materialSymbolsStyle,
    defaultWeight = defaultWeight,
    defaultGrade = defaultGrade,
    defaultOpticalSize = defaultOpticalSize
)

private fun MetaDto.toDomain() = ThemeMeta(
    generatedBy = generatedBy,
    updatedAt = updatedAt
)