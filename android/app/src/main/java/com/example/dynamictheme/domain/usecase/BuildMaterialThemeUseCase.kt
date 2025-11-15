package com.example.dynamictheme.domain.usecase

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.dynamictheme.domain.model.ColorSchemeTokens
import com.example.dynamictheme.domain.model.ElevationTokens
import com.example.dynamictheme.domain.model.IconStyleConfig
import com.example.dynamictheme.domain.model.ShapeTokens
import com.example.dynamictheme.domain.model.ThemeModel
import com.example.dynamictheme.domain.model.ThemePreferences
import com.example.dynamictheme.domain.model.ThemeTokens
import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.model.enums.ThemeSource
import javax.inject.Inject

class BuildMaterialThemeUseCase @Inject constructor() {

    operator fun invoke(
        tokens: ThemeTokens,
        preferences: ThemePreferences,
        iconConfig: IconStyleConfig,
        dynamicColorSupported: Boolean
    ): ThemeModel {
        val isDark = false
        val colorScheme: ColorScheme = if (preferences.useDynamicColor && dynamicColorSupported) {
            if (isDark) darkColorScheme() else lightColorScheme()
        } else {
            val schemeTokens: ColorSchemeTokens? = if (isDark) tokens.colors.dark else tokens.colors.light
            if (schemeTokens != null) {
                toColorScheme(schemeTokens, isDark)
            } else {
                if (isDark) darkColorScheme() else lightColorScheme()
            }
        }

        val typography = buildTypography(tokens, preferences)
        val shapes = buildShapes(tokens.shapes)

        return ThemeModel(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
            elevationTokens = tokens.elevation,
            iconStyle = iconConfig.style,
            iconWeight = iconConfig.weight,
            iconGrade = iconConfig.grade,
            iconOpticalSize = iconConfig.opticalSize,
            themeSource = preferences.themeSource,
            useDynamicColor = preferences.useDynamicColor,
            supportsDynamicColor = dynamicColorSupported,
            themeVersion = tokens.themeVersion,
            lastSyncTimeMillis = preferences.lastSyncTimeMillis
        )
    }

    private fun toColorScheme(tokens: ColorSchemeTokens, dark: Boolean): ColorScheme {
        fun parse(hex: String?, fallback: Color): Color {
            if (hex == null) return fallback
            return runCatching { Color(android.graphics.Color.parseColor(hex)) }.getOrElse { fallback }
        }
        return if (dark) {
            darkColorScheme(
                primary = parse(tokens.primary, Color(0xFFD0BCFF)),
                onPrimary = parse(tokens.onPrimary, Color(0xFF381E72)),
                primaryContainer = parse(tokens.primaryContainer, Color(0xFF4F378B)),
                onPrimaryContainer = parse(tokens.onPrimaryContainer, Color(0xFFEADDFF)),
                secondary = parse(tokens.secondary, Color(0xFFCCC2DC)),
                onSecondary = parse(tokens.onSecondary, Color(0xFF332D41)),
                background = parse(tokens.background, Color(0xFF1C1B1F)),
                onBackground = parse(tokens.onBackground, Color(0xFFE6E1E5)),
                surface = parse(tokens.surface, Color(0xFF1C1B1F)),
                onSurface = parse(tokens.onSurface, Color(0xFFE6E1E5)),
                surfaceVariant = parse(tokens.surfaceVariant, Color(0xFF49454F)),
                onSurfaceVariant = parse(tokens.onSurfaceVariant, Color(0xFFCAC4D0)),
                tertiary = parse(tokens.tertiary, Color(0xFFEFB8C8)),
                error = parse(tokens.error, Color(0xFFF2B8B5)),
                onError = parse(tokens.onError, Color(0xFF601410)),
                outline = parse(tokens.outline, Color(0xFF938F99)),
                inverseSurface = parse(tokens.inverseSurface, Color(0xFFE6E1E5)),
                inverseOnSurface = parse(tokens.inverseOnSurface, Color(0xFF313033))
            )
        } else {
            lightColorScheme(
                primary = parse(tokens.primary, Color(0xFF6750A4)),
                onPrimary = parse(tokens.onPrimary, Color(0xFFFFFFFF)),
                primaryContainer = parse(tokens.primaryContainer, Color(0xFFEADDFF)),
                onPrimaryContainer = parse(tokens.onPrimaryContainer, Color(0xFF21005D)),
                secondary = parse(tokens.secondary, Color(0xFF625B71)),
                onSecondary = parse(tokens.onSecondary, Color(0xFFFFFFFF)),
                background = parse(tokens.background, Color(0xFFFFFBFE)),
                onBackground = parse(tokens.onBackground, Color(0xFF1C1B1F)),
                surface = parse(tokens.surface, Color(0xFFFFFBFE)),
                onSurface = parse(tokens.onSurface, Color(0xFF1C1B1F)),
                surfaceVariant = parse(tokens.surfaceVariant, Color(0xFFE7E0EC)),
                onSurfaceVariant = parse(tokens.onSurfaceVariant, Color(0xFF49454F)),
                tertiary = parse(tokens.tertiary, Color(0xFF7D5260)),
                error = parse(tokens.error, Color(0xFFB3261E)),
                onError = parse(tokens.onError, Color(0xFFFFFFFF)),
                outline = parse(tokens.outline, Color(0xFF79747E)),
                inverseSurface = parse(tokens.inverseSurface, Color(0xFF313033)),
                inverseOnSurface = parse(tokens.inverseOnSurface, Color(0xFFF4EFF4))
            )
        }
    }

    private fun buildTypography(tokens: ThemeTokens, preferences: ThemePreferences): Typography {
        val themeTypography = tokens.typography
        val fontFamilyName = preferences.selectedFontFamily
            ?: themeTypography?.fontFamily
            ?: "Roboto"
        val fontFamily = FontFamily.SansSerif

        fun textStyle(role: String, base: TextStyle): TextStyle {
            val size = themeTypography?.sizeSp?.get(role)?.let { it.sp }
            val lineHeight = themeTypography?.lineHeightSp?.get(role)?.let { it.sp }
            val weightValue = themeTypography?.weightMapping?.get(role)
            val letterSpacingEm = themeTypography?.letterSpacingEm?.get(role)
            return base.copy(
                fontFamily = fontFamily,
                fontWeight = weightValue?.let { FontWeight(it) } ?: base.fontWeight,
                fontSize = size ?: base.fontSize,
                lineHeight = lineHeight ?: base.lineHeight,
                letterSpacing = letterSpacingEm?.toFloat()?.em ?: base.letterSpacing
            )
        }

        return Typography(
            displayLarge = textStyle("displayLarge", Typography().displayLarge),
            headlineMedium = textStyle("headlineMedium", Typography().headlineMedium),
            titleMedium = textStyle("titleMedium", Typography().titleMedium),
            bodyMedium = textStyle("bodyMedium", Typography().bodyMedium),
            labelLarge = textStyle("labelLarge", Typography().labelLarge)
        )
    }

    private fun buildShapes(tokens: ShapeTokens?): Shapes {
        if (tokens == null) return Shapes()
        fun radius(value: Int?): androidx.compose.foundation.shape.CornerSize {
            return androidx.compose.foundation.shape.CornerSize((value ?: 4).toFloat())
        }
        return Shapes(
            extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(radius(tokens.extraSmall)),
            small = androidx.compose.foundation.shape.RoundedCornerShape(radius(tokens.small)),
            medium = androidx.compose.foundation.shape.RoundedCornerShape(radius(tokens.medium)),
            large = androidx.compose.foundation.shape.RoundedCornerShape(radius(tokens.large)),
            extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(radius(tokens.extraLarge))
        )
    }

    private val Float.em
        get() = (this * 1.0f).sp
}