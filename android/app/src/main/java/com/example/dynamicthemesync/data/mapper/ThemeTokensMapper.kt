package com.example.dynamicthemesync.data.mapper

import androidx.compose.ui.graphics.Color
import com.example.dynamicthemesync.domain.model.ColorRoles
import com.example.dynamicthemesync.domain.model.ColorTokens
import com.example.dynamicthemesync.domain.model.ElevationTokens
import com.example.dynamicthemesync.domain.model.IconStyle
import com.example.dynamicthemesync.domain.model.IconTokens
import com.example.dynamicthemesync.domain.model.ShapeTokens
import com.example.dynamicthemesync.domain.model.ThemeTokens
import com.example.dynamicthemesync.domain.model.TypographyTokens
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ThemeTokensDto(
    val schemaVersion: String,
    val themeVersion: String?,
    val name: String?,
    val colors: ColorsDto?,
    val typography: TypographyDto?,
    val shapes: ShapesDto?,
    val elevation: ElevationDto?,
    val icons: IconsDto?
)

@JsonClass(generateAdapter = true)
data class ColorsDto(
    val light: ColorRolesDto?,
    val dark: ColorRolesDto?
)

@JsonClass(generateAdapter = true)
data class ColorRolesDto(
    val primary: String?,
    val onPrimary: String?,
    val primaryContainer: String?,
    val onPrimaryContainer: String?,
    val secondary: String?,
    val onSecondary: String?,
    val background: String?,
    val onBackground: String?,
    val surface: String?,
    val onSurface: String?,
    val surfaceVariant: String?,
    val onSurfaceVariant: String?,
    val tertiary: String?,
    val error: String?,
    val onError: String?,
    val outline: String?,
    val inverseSurface: String?,
    val inverseOnSurface: String?
)

@JsonClass(generateAdapter = true)
data class TypographyDto(
    val fontFamily: String?,
    val fallback: List<String>?,
    val weightMapping: Map<String, Int>?,
    val sizeSp: Map<String, Float>?,
    val lineHeightSp: Map<String, Float>?,
    val letterSpacingEm: Map<String, Float>?
)

@JsonClass(generateAdapter = true)
data class ShapesDto(
    val cornerFamily: String?,
    val extraSmall: Float?,
    val small: Float?,
    val medium: Float?,
    val large: Float?,
    val extraLarge: Float?
)

@JsonClass(generateAdapter = true)
data class ElevationDto(
    val level0: Float?,
    val level1: Float?,
    val level2: Float?,
    val level3: Float?,
    val level4: Float?,
    val level5: Float?
)

@JsonClass(generateAdapter = true)
data class IconsDto(
    @Json(name = "materialSymbolsStyle") val materialSymbolsStyle: String?,
    val defaultWeight: Int?,
    val defaultGrade: Int?,
    val defaultOpticalSize: Int?
)

fun ThemeTokensDto.toDomain(): ThemeTokens {
    return ThemeTokens(
        schemaVersion = schemaVersion,
        themeVersion = themeVersion ?: "",
        name = name ?: "",
        colors = colors?.toDomain(),
        typography = typography?.toDomain(),
        shapes = shapes?.toDomain(),
        elevation = elevation?.toDomain(),
        icons = icons?.toDomain()
    )
}

private fun ColorsDto.toDomain(): ColorTokens = ColorTokens(
    light = light?.toDomain(),
    dark = dark?.toDomain()
)

private fun ColorRolesDto.toDomain(): ColorRoles = ColorRoles(
    primary = primary?.let(::parseColor),
    onPrimary = onPrimary?.let(::parseColor),
    primaryContainer = primaryContainer?.let(::parseColor),
    onPrimaryContainer = onPrimaryContainer?.let(::parseColor),
    secondary = secondary?.let(::parseColor),
    onSecondary = onSecondary?.let(::parseColor),
    background = background?.let(::parseColor),
    onBackground = onBackground?.let(::parseColor),
    surface = surface?.let(::parseColor),
    onSurface = onSurface?.let(::parseColor),
    surfaceVariant = surfaceVariant?.let(::parseColor),
    onSurfaceVariant = onSurfaceVariant?.let(::parseColor),
    tertiary = tertiary?.let(::parseColor),
    error = error?.let(::parseColor),
    onError = onError?.let(::parseColor),
    outline = outline?.let(::parseColor),
    inverseSurface = inverseSurface?.let(::parseColor),
    inverseOnSurface = inverseOnSurface?.let(::parseColor)
)

private fun TypographyDto.toDomain(): TypographyTokens = TypographyTokens(
    fontFamily = fontFamily,
    fallback = fallback ?: emptyList(),
    weightMapping = weightMapping ?: emptyMap(),
    sizeSp = sizeSp ?: emptyMap(),
    lineHeightSp = lineHeightSp ?: emptyMap(),
    letterSpacingEm = letterSpacingEm ?: emptyMap()
)

private fun ShapesDto.toDomain(): ShapeTokens = ShapeTokens(
    cornerFamily = cornerFamily,
    extraSmall = extraSmall,
    small = small,
    medium = medium,
    large = large,
    extraLarge = extraLarge
)

private fun ElevationDto.toDomain(): ElevationTokens = ElevationTokens(
    level0 = level0,
    level1 = level1,
    level2 = level2,
    level3 = level3,
    level4 = level4,
    level5 = level5
)

private fun IconsDto.toDomain(): IconTokens {
    val style = when (materialSymbolsStyle?.lowercase()) {
        "filled" -> IconStyle.FILLED
        "rounded" -> IconStyle.ROUNDED
        else -> IconStyle.OUTLINED
    }
    return IconTokens(
        materialSymbolsStyle = style,
        defaultWeight = defaultWeight ?: 400,
        defaultGrade = defaultGrade ?: 0,
        defaultOpticalSize = defaultOpticalSize ?: 24
    )
}

private fun parseColor(hex: String): Color {
    val clean = hex.removePrefix("#")
    val value = clean.toLong(16)
    return when (clean.length) {
        6 -> Color(value or 0x00000000FF000000L)
        8 -> Color(value)
        else -> Color(0xFF000000)
    }
}