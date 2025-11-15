package com.example.dynamicmaterialthemesync.domain.usecase

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dynamicmaterialthemesync.domain.model.ColorRoleTokens
import com.example.dynamicmaterialthemesync.domain.model.ElevationTokens
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.model.ShapeTokens
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider

class BuildMaterialThemeUseCase {
    operator fun invoke(
        tokens: ThemeTokens?,
        preferences: ThemePreferences,
        iconStyleConfig: IconStyleConfig,
        dynamicColorProvider: DynamicColorProvider
    ): ThemeModel {
        val useDynamic = preferences.useDynamicColor && dynamicColorProvider.isSupported()

        val lightScheme: ColorScheme
        val darkScheme: ColorScheme

        if (useDynamic) {
            lightScheme = dynamicColorProvider.getLightScheme()
            darkScheme = dynamicColorProvider.getDarkScheme()
        } else {
            val lightRoles = tokens?.colors?.light
            val darkRoles = tokens?.colors?.dark
            lightScheme = buildColorSchemeFromTokens(lightRoles, isDark = false)
            darkScheme = buildColorSchemeFromTokens(darkRoles, isDark = true)
        }

        val typography = buildTypography(tokens?.typography, preferences.selectedFontFamily)
        val shapes = buildShapes(tokens?.shapes)
        val elevation = tokens?.elevation ?: ElevationTokens(0, 1, 3, 6, 8, 12)

        return ThemeModel(
            colorSchemeLight = lightScheme,
            colorSchemeDark = darkScheme,
            typography = typography,
            shapes = shapes,
            elevationTokens = elevation,
            iconStyleConfig = iconStyleConfig,
            themeSource = preferences.themeSource,
            themeVersion = tokens?.themeVersion,
            lastSyncTimeMillis = preferences.lastSyncTimeMillis
        )
    }

    private fun buildColorSchemeFromTokens(tokens: ColorRoleTokens?, isDark: Boolean): ColorScheme {
        return if (tokens == null) {
            if (isDark) darkColorScheme() else lightColorScheme()
        } else {
            if (isDark) {
                darkColorScheme(
                    primary = tokens.primary ?: Color(0xFFD0BCFF),
                    onPrimary = tokens.onPrimary ?: Color(0xFF381E72),
                    primaryContainer = tokens.primaryContainer ?: Color(0xFF4F378B),
                    onPrimaryContainer = tokens.onPrimaryContainer ?: Color(0xFFEADDFF),
                    secondary = tokens.secondary ?: Color(0xFFCCC2DC),
                    onSecondary = tokens.onSecondary ?: Color(0xFF332D41),
                    background = tokens.background ?: Color(0xFF1C1B1F),
                    onBackground = tokens.onBackground ?: Color(0xFFE6E1E5),
                    surface = tokens.surface ?: Color(0xFF1C1B1F),
                    onSurface = tokens.onSurface ?: Color(0xFFE6E1E5),
                    surfaceVariant = tokens.surfaceVariant ?: Color(0xFF49454F),
                    onSurfaceVariant = tokens.onSurfaceVariant ?: Color(0xFFCAC4D0),
                    tertiary = tokens.tertiary ?: Color(0xFFEFB8C8),
                    error = tokens.error ?: Color(0xFFF2B8B5),
                    onError = tokens.onError ?: Color(0xFF601410),
                    outline = tokens.outline ?: Color(0xFF938F99)
                )
            } else {
                lightColorScheme(
                    primary = tokens.primary ?: Color(0xFF6750A4),
                    onPrimary = tokens.onPrimary ?: Color(0xFFFFFFFF),
                    primaryContainer = tokens.primaryContainer ?: Color(0xFFEADDFF),
                    onPrimaryContainer = tokens.onPrimaryContainer ?: Color(0xFF21005D),
                    secondary = tokens.secondary ?: Color(0xFF625B71),
                    onSecondary = tokens.onSecondary ?: Color(0xFFFFFFFF),
                    background = tokens.background ?: Color(0xFFFFFBFE),
                    onBackground = tokens.onBackground ?: Color(0xFF1C1B1F),
                    surface = tokens.surface ?: Color(0xFFFFFBFE),
                    onSurface = tokens.onSurface ?: Color(0xFF1C1B1F),
                    surfaceVariant = tokens.surfaceVariant ?: Color(0xFFE7E0EC),
                    onSurfaceVariant = tokens.onSurfaceVariant ?: Color(0xFF49454F),
                    tertiary = tokens.tertiary ?: Color(0xFF7D5260),
                    error = tokens.error ?: Color(0xFFB3261E),
                    onError = tokens.onError ?: Color(0xFFFFFFFF),
                    outline = tokens.outline ?: Color(0xFF79747E)
                )
            }
        }
    }

    private fun buildTypography(tokens: com.example.dynamicmaterialthemesync.domain.model.TypographyTokens?, selectedFamily: String?): Typography {
        // Simplified: use Material defaults; a real impl would map weights/sizes per style
        return Typography()
    }

    private fun buildShapes(tokens: ShapeTokens?): Shapes {
        return if (tokens == null) {
            Shapes()
        } else {
            Shapes(
                extraSmall = (tokens.extraSmall ?: 4).dp,
                small = (tokens.small ?: 8).dp,
                medium = (tokens.medium ?: 12).dp,
                large = (tokens.large ?: 16).dp,
                extraLarge = (tokens.extraLarge ?: 28).dp
            )
        }
    }
}