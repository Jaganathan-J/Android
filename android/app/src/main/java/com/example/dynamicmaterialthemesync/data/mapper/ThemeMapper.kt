package com.example.dynamicmaterialthemesync.data.mapper

import androidx.compose.ui.graphics.Color
import com.example.dynamicmaterialthemesync.data.source.remote.ColorSchemeJson
import com.example.dynamicmaterialthemesync.data.source.remote.ColorsJson
import com.example.dynamicmaterialthemesync.data.source.remote.ElevationJson
import com.example.dynamicmaterialthemesync.data.source.remote.IconsJson
import com.example.dynamicmaterialthemesync.data.source.remote.MetaJson
import com.example.dynamicmaterialthemesync.data.source.remote.ShapesJson
import com.example.dynamicmaterialthemesync.data.source.remote.ThemeJsonResponse
import com.example.dynamicmaterialthemesync.data.source.remote.TypographyJson
import com.example.dynamicmaterialthemesync.domain.model.ColorSchemeTokens
import com.example.dynamicmaterialthemesync.domain.model.ColorTokens
import com.example.dynamicmaterialthemesync.domain.model.ElevationTokens
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.IconTokens
import com.example.dynamicmaterialthemesync.domain.model.ShapeTokens
import com.example.dynamicmaterialthemesync.domain.model.ThemeMeta
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.model.TypographyTokens

fun ThemeJsonResponse.toDomain(): ThemeTokens {
    return ThemeTokens(
        schemaVersion = schemaVersion,
        themeVersion = themeVersion,
        name = name,
        colors = colors?.toDomain(),
        typography = typography?.toDomain(),
        shapes = shapes?.toDomain(),
        elevation = elevation?.toDomain(),
        icons = icons?.toDomain(),
        meta = meta?.toDomain()
    )
}

private fun ColorsJson.toDomain(): ColorTokens {
    return ColorTokens(
        light = light?.toDomain(),
        dark = dark?.toDomain()
    )
}

private fun ColorSchemeJson.toDomain(): ColorSchemeTokens {
    return ColorSchemeTokens(
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
}

private fun TypographyJson.toDomain(): TypographyTokens {
    return TypographyTokens(
        fontFamily = fontFamily,
        fallback = fallback,
        weightMapping = weightMapping,
        sizeSp = sizeSp,
        lineHeightSp = lineHeightSp,
        letterSpacingEm = letterSpacingEm
    )
}

private fun ShapesJson.toDomain(): ShapeTokens {
    return ShapeTokens(
        cornerFamily = cornerFamily,
        extraSmall = extraSmall,
        small = small,
        medium = medium,
        large = large,
        extraLarge = extraLarge
    )
}

private fun ElevationJson.toDomain(): ElevationTokens {
    return ElevationTokens(
        level0 = level0,
        level1 = level1,
        level2 = level2,
        level3 = level3,
        level4 = level4,
        level5 = level5
    )
}

private fun IconsJson.toDomain(): IconTokens {
    val style = when (materialSymbolsStyle?.lowercase()) {
        "outlined" -> IconStyle.OUTLINED
        "rounded" -> IconStyle.ROUNDED
        else -> IconStyle.FILLED
    }
    return IconTokens(
        materialSymbolsStyle = style,
        defaultWeight = defaultWeight,
        defaultGrade = defaultGrade,
        defaultOpticalSize = defaultOpticalSize
    )
}

private fun MetaJson.toDomain(): ThemeMeta {
    return ThemeMeta(
        generatedBy = generatedBy,
        updatedAt = updatedAt
    )
}

private fun parseColor(hex: String): Color {
    val clean = hex.removePrefix("#")
    val colorLong = clean.toLong(16)
    return when (clean.length) {
        6 -> Color(colorLong or 0xFF000000)
        8 -> Color(colorLong)
        else -> Color(0xFF000000)
    }
}