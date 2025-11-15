package com.example.dynamicmaterialthemesync.domain.usecase

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dynamicmaterialthemesync.domain.model.ElevationLevels
import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
import javax.inject.Inject

class BuildMaterialThemeUseCase @Inject constructor(
    private val dynamicColorProvider: DynamicColorProvider
) {
    operator fun invoke(
        tokens: ThemeTokens?,
        preferences: ThemePreferences,
        fontConfig: FontConfig?,
        iconConfig: IconStyleConfig,
        supportsDynamicColor: Boolean,
        lastSyncTimeMillis: Long?,
        themeVersion: String?
    ): ThemeModel {
        val useDynamic = preferences.useDynamicColor && supportsDynamicColor

        val lightScheme: ColorScheme
        val darkScheme: ColorScheme

        if (useDynamic) {
            lightScheme = dynamicColorProvider.getLightScheme()
            darkScheme = dynamicColorProvider.getDarkScheme()
        } else {
            val lightTokens = tokens?.colors?.light
            val darkTokens = tokens?.colors?.dark ?: lightTokens

            lightScheme = colorSchemeFromTokens(lightTokens) ?: lightColorScheme()
            darkScheme = colorSchemeFromTokens(darkTokens) ?: darkColorScheme()
        }

        val typography = typographyFromTokens(tokens, fontConfig)
        val shapes = shapesFromTokens(tokens)
        val elevation = elevationFromTokens(tokens)

        return ThemeModel(
            colorSchemeLight = lightScheme,
            colorSchemeDark = darkScheme,
            typography = typography,
            shapes = shapes,
            elevation = elevation,
            iconStyleConfig = iconConfig,
            themeSource = preferences.themeSource,
            themeVersion = themeVersion ?: tokens?.themeVersion,
            lastSyncTimeMillis = lastSyncTimeMillis,
            supportsDynamicColor = supportsDynamicColor,
            useDynamicColor = useDynamic
        )
    }

    private fun colorSchemeFromTokens(tokens: com.example.dynamicmaterialthemesync.domain.model.ColorSchemeTokens?): ColorScheme? {
        if (tokens == null) return null
        fun parse(hex: String?, fallback: Color): Color = hex?.let { Color(android.graphics.Color.parseColor(it)) } ?: fallback
        val base = lightColorScheme()
        return lightColorScheme(
            primary = parse(tokens.primary, base.primary),
            onPrimary = parse(tokens.onPrimary, base.onPrimary),
            primaryContainer = parse(tokens.primaryContainer, base.primaryContainer),
            onPrimaryContainer = parse(tokens.onPrimaryContainer, base.onPrimaryContainer),
            secondary = parse(tokens.secondary, base.secondary),
            onSecondary = parse(tokens.onSecondary, base.onSecondary),
            background = parse(tokens.background, base.background),
            onBackground = parse(tokens.onBackground, base.onBackground),
            surface = parse(tokens.surface, base.surface),
            onSurface = parse(tokens.onSurface, base.onSurface),
            surfaceVariant = parse(tokens.surfaceVariant, base.surfaceVariant),
            onSurfaceVariant = parse(tokens.onSurfaceVariant, base.onSurfaceVariant),
            tertiary = parse(tokens.tertiary, base.tertiary),
            error = parse(tokens.error, base.error),
            onError = parse(tokens.onError, base.onError),
            outline = parse(tokens.outline, base.outline),
            inverseSurface = parse(tokens.inverseSurface, base.inverseSurface),
            inverseOnSurface = parse(tokens.inverseOnSurface, base.inverseOnSurface)
        )
    }

    private fun typographyFromTokens(tokens: ThemeTokens?, fontConfig: FontConfig?): Typography {
        val base = Typography()
        val fontFamily = fontConfig?.family?.let { FontFamily.SansSerif } ?: base.bodyMedium.fontFamily
        // For brevity: use base typography but apply chosen family to core styles
        return Typography(
            displayLarge = base.displayLarge.copy(fontFamily = fontFamily),
            headlineMedium = base.headlineMedium.copy(fontFamily = fontFamily),
            titleMedium = base.titleMedium.copy(fontFamily = fontFamily),
            bodyMedium = base.bodyMedium.copy(fontFamily = fontFamily),
            labelLarge = base.labelLarge.copy(fontFamily = fontFamily)
        )
    }

    private fun shapesFromTokens(tokens: ThemeTokens?): Shapes {
        val base = Shapes()
        val s = tokens?.shapes
        fun radius(value: Int?, fallback: Int): Float = (value ?: fallback).toFloat()
        return Shapes(
            extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(radius(s?.extraSmall, 4).dp),
            small = androidx.compose.foundation.shape.RoundedCornerShape(radius(s?.small, 8).dp),
            medium = androidx.compose.foundation.shape.RoundedCornerShape(radius(s?.medium, 12).dp),
            large = androidx.compose.foundation.shape.RoundedCornerShape(radius(s?.large, 16).dp),
            extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(radius(s?.extraLarge, 28).dp)
        )
    }

    private fun elevationFromTokens(tokens: ThemeTokens?): ElevationLevels {
        val e = tokens?.elevation
        fun lvl(v: Int?, fallback: Int) = (v ?: fallback).toFloat()
        return ElevationLevels(
            level0 = lvl(e?.level0, 0),
            level1 = lvl(e?.level1, 1),
            level2 = lvl(e?.level2, 3),
            level3 = lvl(e?.level3, 6),
            level4 = lvl(e?.level4, 8),
            level5 = lvl(e?.level5, 12)
        )
    }
}