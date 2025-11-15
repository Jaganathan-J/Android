package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ValidateThemeJsonUseCase @Inject constructor() {
    operator fun invoke(tokens: ThemeTokens) {
        val schema = tokens.schemaVersion
        if (!schema.startsWith("1.")) {
            throw IllegalArgumentException("Unsupported schema version: $schema")
        }
        val light = tokens.colors?.light
        val dark = tokens.colors?.dark
        if (light?.primary == null || light.onPrimary == null || light.background == null || light.surface == null || light.onSurface == null) {
            throw IllegalArgumentException("Missing required light color roles")
        }
        if (dark?.primary == null || dark.onPrimary == null || dark.background == null || dark.surface == null || dark.onSurface == null) {
            throw IllegalArgumentException("Missing required dark color roles")
        }
        val shapes = tokens.shapes
        if (shapes != null) {
            val radii = listOfNotNull(shapes.extraSmall, shapes.small, shapes.medium, shapes.large, shapes.extraLarge)
            if (radii.any { it < 0 }) {
                throw IllegalArgumentException("Corner radii must be non-negative")
            }
        }
        val elevation = tokens.elevation
        if (elevation != null) {
            val levels = listOfNotNull(
                elevation.level0,
                elevation.level1,
                elevation.level2,
                elevation.level3,
                elevation.level4,
                elevation.level5
            )
            if (levels != levels.sorted()) {
                throw IllegalArgumentException("Elevation levels must be non-decreasing")
            }
        }
        val iconStyle = tokens.icons?.materialSymbolsStyle
        if (iconStyle == null) {
            // allow null, will fallback
        }
    }
}