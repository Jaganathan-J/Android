package com.example.dynamicmaterialthemesync.domain.model

/**
 * Domain-level representation of theme tokens.
 * This is a normalized, presentation-friendly structure built from the
 * API response and used throughout the app as a single source of truth.
 */
data class ThemeTokens(
    val colorTokens: List<ThemeColorToken>,
    val typographyTokens: List<ThemeTypographyToken>,
    val shapeTokens: List<ThemeShapeToken>,
    val elevationTokens: List<ThemeElevationToken>,
    val version: String? = null
)