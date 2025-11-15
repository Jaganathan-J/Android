package com.example.dynamicmaterialthemesync.domain.model

import androidx.compose.ui.graphics.Color

data class ThemeTokens(
    val schemaVersion: String,
    val themeVersion: String?,
    val name: String?,
    val colors: ColorTokens?,
    val typography: TypographyTokens?,
    val shapes: ShapeTokens?,
    val elevation: ElevationTokens?,
    val icons: IconTokens?,
    val meta: ThemeMeta?
)

data class ColorTokens(
    val light: ColorSchemeTokens?,
    val dark: ColorSchemeTokens?
)

data class ColorSchemeTokens(
    val primary: Color?,
    val onPrimary: Color?,
    val primaryContainer: Color?,
    val onPrimaryContainer: Color?,
    val secondary: Color?,
    val onSecondary: Color?,
    val background: Color?,
    val onBackground: Color?,
    val surface: Color?,
    val onSurface: Color?,
    val surfaceVariant: Color?,
    val onSurfaceVariant: Color?,
    val tertiary: Color?,
    val error: Color?,
    val onError: Color?,
    val outline: Color?,
    val inverseSurface: Color?,
    val inverseOnSurface: Color?
)

data class TypographyTokens(
    val fontFamily: String?,
    val fallback: List<String>?,
    val weightMapping: Map<String, Int>?,
    val sizeSp: Map<String, Int>?,
    val lineHeightSp: Map<String, Int>?,
    val letterSpacingEm: Map<String, Float>?
)

data class ShapeTokens(
    val cornerFamily: String?,
    val extraSmall: Int?,
    val small: Int?,
    val medium: Int?,
    val large: Int?,
    val extraLarge: Int?
)

data class ElevationTokens(
    val level0: Int?,
    val level1: Int?,
    val level2: Int?,
    val level3: Int?,
    val level4: Int?,
    val level5: Int?
)

data class IconTokens(
    val materialSymbolsStyle: IconStyle?,
    val defaultWeight: Int?,
    val defaultGrade: Int?,
    val defaultOpticalSize: Int?
)

data class ThemeMeta(
    val generatedBy: String?,
    val updatedAt: String?
)

enum class IconStyle { FILLED, OUTLINED, ROUNDED }