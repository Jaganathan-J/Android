package com.example.dynamicthemesync.domain.usecase

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dynamicthemesync.domain.model.ColorRoles
import com.example.dynamicthemesync.domain.model.ElevationTokens
import com.example.dynamicthemesync.domain.model.FontConfig
import com.example.dynamicthemesync.domain.model.IconStyleConfig
import com.example.dynamicthemesync.domain.model.ShapeTokens
import com.example.dynamicthemesync.domain.model.ThemeModel
import com.example.dynamicthemesync.domain.model.ThemePreferences
import com.example.dynamicthemesync.domain.model.ThemeTokens
import com.example.dynamicthemesync.domain.repository.DynamicColorProvider
import javax.inject.Inject

class BuildMaterialThemeUseCase @Inject constructor() {

    operator fun invoke(
        tokens: ThemeTokens?,
        preferences: ThemePreferences,
        fontConfig: FontConfig?,
        iconStyleConfig: IconStyleConfig,
        dynamicColorProvider: DynamicColorProvider
    ): ThemeModel {
        val colorSchemes = buildColorSchemes(tokens, preferences, dynamicColorProvider)
        val typography = buildTypography(tokens?.typography, fontConfig)
        val shapes = buildShapes(tokens?.shapes)
        val elevation = tokens?.elevation ?: ElevationTokens(0f, 1f, 3f, 6f, 8f, 12f)
        val themeName = tokens?.name ?: "Default"
        val themeVersion = tokens?.themeVersion

        return ThemeModel(
            colorSchemeLight = colorSchemes.first,
            colorSchemeDark = colorSchemes.second,
            typography = typography,
            shapes = shapes,
            elevation = elevation,
            iconStyleConfig = iconStyleConfig,
            preferences = preferences,
            themeName = themeName,
            themeVersion = themeVersion
        )
    }

    private fun buildColorSchemes(
        tokens: ThemeTokens?,
        preferences: ThemePreferences,
        dynamicColorProvider: DynamicColorProvider
    ): Pair<ColorScheme, ColorScheme> {
        val canUseDynamic = dynamicColorProvider.isSupported() && preferences.useDynamicColor
        if (canUseDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return dynamicColorProvider.getLightScheme() to dynamicColorProvider.getDarkScheme()
        }

        val lightRoles: ColorRoles? = tokens?.colors?.light
        val darkRoles: ColorRoles? = tokens?.colors?.dark

        val light = lightColorScheme(
            primary = lightRoles?.primary ?: Color(0xFF6750A4),
            onPrimary = lightRoles?.onPrimary ?: Color.White,
            primaryContainer = lightRoles?.primaryContainer ?: Color(0xFFEADDFF),
            onPrimaryContainer = lightRoles?.onPrimaryContainer ?: Color(0xFF21005D),
            secondary = lightRoles?.secondary ?: Color(0xFF625B71),
            onSecondary = lightRoles?.onSecondary ?: Color.White,
            background = lightRoles?.background ?: Color(0xFFFFFBFE),
            onBackground = lightRoles?.onBackground ?: Color(0xFF1C1B1F),
            surface = lightRoles?.surface ?: Color(0xFFFFFBFE),
            onSurface = lightRoles?.onSurface ?: Color(0xFF1C1B1F),
            surfaceVariant = lightRoles?.surfaceVariant ?: Color(0xFFE7E0EC),
            onSurfaceVariant = lightRoles?.onSurfaceVariant ?: Color(0xFF49454F),
            tertiary = lightRoles?.tertiary ?: Color(0xFF7D5260),
            error = lightRoles?.error ?: Color(0xFFB3261E),
            onError = lightRoles?.onError ?: Color.White,
            outline = lightRoles?.outline ?: Color(0xFF79747E),
            inverseSurface = lightRoles?.inverseSurface ?: Color(0xFF313033),
            inverseOnSurface = lightRoles?.inverseOnSurface ?: Color(0xFFF4EFF4)
        )

        val dark = darkColorScheme(
            primary = darkRoles?.primary ?: Color(0xFFD0BCFF),
            onPrimary = darkRoles?.onPrimary ?: Color(0xFF381E72),
            primaryContainer = darkRoles?.primaryContainer ?: Color(0xFF4F378B),
            onPrimaryContainer = darkRoles?.onPrimaryContainer ?: Color(0xFFEADDFF),
            secondary = darkRoles?.secondary ?: Color(0xFFCCC2DC),
            onSecondary = darkRoles?.onSecondary ?: Color(0xFF332D41),
            background = darkRoles?.background ?: Color(0xFF1C1B1F),
            onBackground = darkRoles?.onBackground ?: Color(0xFFE6E1E5),
            surface = darkRoles?.surface ?: Color(0xFF1C1B1F),
            onSurface = darkRoles?.onSurface ?: Color(0xFFE6E1E5),
            surfaceVariant = darkRoles?.surfaceVariant ?: Color(0xFF49454F),
            onSurfaceVariant = darkRoles?.onSurfaceVariant ?: Color(0xFFCAC4D0),
            tertiary = darkRoles?.tertiary ?: Color(0xFFEFB8C8),
            error = darkRoles?.error ?: Color(0xFFF2B8B5),
            onError = darkRoles?.onError ?: Color(0xFF601410),
            outline = darkRoles?.outline ?: Color(0xFF938F99),
            inverseSurface = darkRoles?.inverseSurface ?: Color(0xFFE6E1E5),
            inverseOnSurface = darkRoles?.inverseOnSurface ?: Color(0xFF313033)
        )

        return light to dark
    }

    private fun buildTypography(tokens: com.example.dynamicthemesync.domain.model.TypographyTokens?, fontConfig: FontConfig?): Typography {
        val family = if (fontConfig != null) {
            FontFamily.SansSerif
        } else {
            FontFamily.SansSerif
        }

        val default = Typography()

        fun styleFor(key: String, base: TextStyle): TextStyle {
            val size = tokens?.sizeSp?.get(key)?.sp ?: base.fontSize
            val lineHeight = tokens?.lineHeightSp?.get(key)?.sp ?: base.lineHeight
            val weightValue = tokens?.weightMapping?.get(key) ?: when (base.fontWeight) {
                FontWeight.Bold -> 700
                FontWeight.Medium -> 500
                else -> 400
            }
            val letterEm = tokens?.letterSpacingEm?.get(key) ?: 0f
            return base.copy(
                fontFamily = family,
                fontSize = size,
                lineHeight = lineHeight,
                fontWeight = FontWeight(weightValue),
                letterSpacing = (letterEm * size.value).sp / 10
            )
        }

        return Typography(
            displayLarge = styleFor("displayLarge", default.displayLarge),
            headlineMedium = styleFor("headlineMedium", default.headlineMedium),
            titleMedium = styleFor("titleMedium", default.titleMedium),
            bodyMedium = styleFor("bodyMedium", default.bodyMedium),
            labelLarge = styleFor("labelLarge", default.labelLarge)
        )
    }

    private fun buildShapes(tokens: ShapeTokens?): Shapes {
        if (tokens == null) return Shapes()
        val extraSmall = (tokens.extraSmall ?: 4f).dp
        val small = (tokens.small ?: 8f).dp
        val medium = (tokens.medium ?: 12f).dp
        val large = (tokens.large ?: 16f).dp
        val extraLarge = (tokens.extraLarge ?: 28f).dp
        return Shapes(
            extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(extraSmall),
            small = androidx.compose.foundation.shape.RoundedCornerShape(small),
            medium = androidx.compose.foundation.shape.RoundedCornerShape(medium),
            large = androidx.compose.foundation.shape.RoundedCornerShape(large),
            extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(extraLarge)
        )
    }
}