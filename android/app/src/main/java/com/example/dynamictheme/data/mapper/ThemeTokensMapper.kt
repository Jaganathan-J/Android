package com.example.dynamictheme.data.mapper

import com.example.dynamictheme.data.remote.dto.ColorSchemeTokensDto
import com.example.dynamictheme.data.remote.dto.ColorTokensDto
import com.example.dynamictheme.data.remote.dto.ElevationTokensDto
import com.example.dynamictheme.data.remote.dto.IconTokensDto
import com.example.dynamictheme.data.remote.dto.ShapeTokensDto
import com.example.dynamictheme.data.remote.dto.ThemeMetaDto
import com.example.dynamictheme.data.remote.dto.ThemeTokensDto
import com.example.dynamictheme.data.remote.dto.TypographyTokensDto
import com.example.dynamictheme.domain.model.ColorSchemeTokens
import com.example.dynamictheme.domain.model.ColorTokens
import com.example.dynamictheme.domain.model.ElevationTokens
import com.example.dynamictheme.domain.model.IconTokens
import com.example.dynamictheme.domain.model.ShapeTokens
import com.example.dynamictheme.domain.model.ThemeMeta
import com.example.dynamictheme.domain.model.ThemeTokens
import com.example.dynamictheme.domain.model.TypographyTokens

fun ThemeTokensDto.toDomain(): ThemeTokens {
    return ThemeTokens(
        schemaVersion = schemaVersion,
        themeVersion = themeVersion ?: "",
        name = name ?: "",
        colors = colors?.toDomain() ?: ColorTokens(null, null),
        typography = typography?.toDomain(),
        shapes = shapes?.toDomain(),
        elevation = elevation?.toDomain(),
        icons = icons?.toDomain(),
        meta = meta?.toDomain()
    )
}

private fun ColorTokensDto.toDomain(): ColorTokens = ColorTokens(
    light = light?.toDomain(),
    dark = dark?.toDomain()
)

private fun ColorSchemeTokensDto.toDomain(): ColorSchemeTokens = ColorSchemeTokens(
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

private fun TypographyTokensDto.toDomain(): TypographyTokens = TypographyTokens(
    fontFamily = fontFamily,
    fallback = fallback,
    weightMapping = weightMapping,
    sizeSp = sizeSp,
    lineHeightSp = lineHeightSp,
    letterSpacingEm = letterSpacingEm
)

private fun ShapeTokensDto.toDomain(): ShapeTokens = ShapeTokens(
    cornerFamily = cornerFamily,
    extraSmall = extraSmall,
    small = small,
    medium = medium,
    large = large,
    extraLarge = extraLarge
)

private fun ElevationTokensDto.toDomain(): ElevationTokens = ElevationTokens(
    level0 = level0,
    level1 = level1,
    level2 = level2,
    level3 = level3,
    level4 = level4,
    level5 = level5
)

private fun IconTokensDto.toDomain(): IconTokens = IconTokens(
    materialSymbolsStyle = materialSymbolsStyle,
    defaultWeight = defaultWeight,
    defaultGrade = defaultGrade,
    defaultOpticalSize = defaultOpticalSize
)

private fun ThemeMetaDto.toDomain(): ThemeMeta = ThemeMeta(
    generatedBy = generatedBy,
    updatedAt = updatedAt
)