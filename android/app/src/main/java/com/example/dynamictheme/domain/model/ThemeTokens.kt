package com.example.dynamictheme.domain.model

import kotlin.collections.Map

data class ThemeTokens(
    val schemaVersion: String,
    val themeVersion: String,
    val name: String,
    val colors: ColorTokens,
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

data class TypographyTokens(
    val fontFamily: String?,
    val fallback: List<String>?,
    val weightMapping: Map<String, Int>?,
    val sizeSp: Map<String, Int>?,
    val lineHeightSp: Map<String, Int>?,
    val letterSpacingEm: Map<String, Double>?
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
    val materialSymbolsStyle: String?,
    val defaultWeight: Int?,
    val defaultGrade: Int?,
    val defaultOpticalSize: Int?
)

data class ThemeMeta(
    val generatedBy: String?,
    val updatedAt: String?
)