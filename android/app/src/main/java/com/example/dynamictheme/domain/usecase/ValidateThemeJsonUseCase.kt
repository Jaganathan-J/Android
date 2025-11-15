package com.example.dynamictheme.domain.usecase

import com.example.dynamictheme.domain.model.ThemeTokens
import javax.inject.Inject

class ValidateThemeJsonUseCase @Inject constructor() {
    operator fun invoke(tokens: ThemeTokens): Boolean {
        if (!tokens.schemaVersion.startsWith("1.")) return false
        val light = tokens.colors.light
        val dark = tokens.colors.dark
        fun hasRequired(c: com.example.dynamictheme.domain.model.ColorSchemeTokens?): Boolean {
            if (c == null) return false
            return c.primary != null && c.onPrimary != null && c.background != null && c.surface != null && c.onSurface != null
        }
        if (!hasRequired(light) && !hasRequired(dark)) return false
        if (tokens.elevation != null) {
            val levels = listOfNotNull(
                tokens.elevation.level0,
                tokens.elevation.level1,
                tokens.elevation.level2,
                tokens.elevation.level3,
                tokens.elevation.level4,
                tokens.elevation.level5
            )
            if (levels.any { it < 0 }) return false
        }
        if (tokens.shapes != null) {
            val shapes = tokens.shapes
            val radii = listOfNotNull(
                shapes.extraSmall,
                shapes.small,
                shapes.medium,
                shapes.large,
                shapes.extraLarge
            )
            if (radii.any { it < 0 }) return false
        }
        if (tokens.icons != null) {
            val style = tokens.icons.materialSymbolsStyle?.lowercase()
            if (style != null && style !in setOf("filled", "outlined", "rounded")) return false
        }
        return true
    }
}