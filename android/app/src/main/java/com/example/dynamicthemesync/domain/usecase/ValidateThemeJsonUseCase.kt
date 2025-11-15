package com.example.dynamicthemesync.domain.usecase

import com.example.dynamicthemesync.domain.model.ThemeTokens
import javax.inject.Inject

class ValidateThemeJsonUseCase @Inject constructor() {

    operator fun invoke(tokens: ThemeTokens): Result<Unit> {
        val schema = tokens.schemaVersion
        if (!schema.startsWith("1.")) {
            return Result.failure(IllegalArgumentException("Unsupported schema version: $schema"))
        }
        val light = tokens.colors?.light
        val dark = tokens.colors?.dark
        if (light == null || light.primary == null || light.onPrimary == null || light.background == null || light.surface == null || light.onSurface == null) {
            return Result.failure(IllegalArgumentException("Missing required light color roles"))
        }
        if (dark == null || dark.primary == null || dark.onPrimary == null || dark.background == null || dark.surface == null || dark.onSurface == null) {
            return Result.failure(IllegalArgumentException("Missing required dark color roles"))
        }
        val shapes = tokens.shapes
        if (shapes != null) {
            listOfNotNull(shapes.extraSmall, shapes.small, shapes.medium, shapes.large, shapes.extraLarge)
                .forEach { if (it < 0f) return Result.failure(IllegalArgumentException("Negative corner radius")) }
        }
        val elevation = tokens.elevation
        if (elevation != null) {
            val levels = listOfNotNull(elevation.level0, elevation.level1, elevation.level2, elevation.level3, elevation.level4, elevation.level5)
            if (levels != levels.sorted()) {
                return Result.failure(IllegalArgumentException("Elevation levels must be non-decreasing"))
            }
        }
        val icons = tokens.icons
        if (icons != null) {
            val style = icons.materialSymbolsStyle
            if (style.name !in listOf("FILLED", "OUTLINED", "ROUNDED")) {
                return Result.failure(IllegalArgumentException("Unsupported icon style"))
            }
        }
        return Result.success(Unit)
    }
}