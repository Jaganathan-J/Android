package com.example.dynamicmaterialthemesync.domain.usecase

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Shapes as M3Shapes
import androidx.compose.material3.Typography as M3Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dynamicmaterialthemesync.domain.model.ColorSchemeTokens
import com.example.dynamicmaterialthemesync.domain.model.ElevationTokens
import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import com.example.dynamicmaterialthemesync.domain.model.IconStyleConfig
import com.example.dynamicmaterialthemesync.domain.model.ShapeTokens
import com.example.dynamicmaterialthemesync.domain.model.ThemeModel
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
import javax.inject.Inject

class BuildMaterialThemeUseCase @Inject constructor() {

    operator fun invoke(
        preferences: ThemePreferences,
        tokens: ThemeTokens?,
        fontConfig: FontConfig?,
        iconStyleConfig: IconStyleConfig,
        dynamicColorProvider: DynamicColorProvider,
        lastSyncInfo: Pair<String?, Long?>
    ): ThemeModel {
        val supportsDynamic = dynamicColorProvider.isSupported()
        val useDynamicColor = preferences.useDynamicColor && supportsDynamic

        val lightScheme: ColorScheme
        val darkScheme: ColorScheme

        if (useDynamicColor) {
            lightScheme = dynamicColorProvider.getLightScheme()
            darkScheme = dynamicColorProvider.getDarkScheme()
        } else {
            val lightTokens = tokens?.colors?.light
            val darkTokens = tokens?.colors?.dark
            lightScheme = if (lightTokens != null) mapColorScheme(lightTokens, isDark = false) else defaultLightScheme()
            darkScheme = if (darkTokens != null) mapColorScheme(darkTokens, isDark = true) else defaultDarkScheme()
        }

        val shapesTokens: ShapeTokens? = tokens?.shapes
        val shapes = mapShapes(shapesTokens)

        val typography = mapTypography(tokens, fontConfig)

        val colorScheme = lightScheme

        return ThemeModel(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
            elevationTokens = tokens?.elevation,
            iconStyleConfig = iconStyleConfig,
            themeSource = preferences.themeSource,
            useDynamicColor = useDynamicColor,
            supportsDynamicColor = supportsDynamic,
            themeVersion = tokens?.themeVersion ?: lastSyncInfo.first,
            lastSyncTimeMillis = lastSyncInfo.second
        )
    }

    private fun mapColorScheme(tokens: ColorSchemeTokens, isDark: Boolean): ColorScheme {
        return if (isDark) {
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
                outline = tokens.outline ?: Color(0xFF938F99),
                inverseSurface = tokens.inverseSurface ?: Color(0xFFE6E1E5),
                inverseOnSurface = tokens.inverseOnSurface ?: Color(0xFF313033)
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
                outline = tokens.outline ?: Color(0xFF79747E),
                inverseSurface = tokens.inverseSurface ?: Color(0xFF313033),
                inverseOnSurface = tokens.inverseOnSurface ?: Color(0xFFF4EFF4)
            )
        }
    }

    private fun defaultLightScheme(): ColorScheme = lightColorScheme()

    private fun defaultDarkScheme(): ColorScheme = darkColorScheme()

    private fun mapShapes(tokens: ShapeTokens?): Shapes {
        val xs = (tokens?.extraSmall ?: 4).dp
        val s = (tokens?.small ?: 8).dp
        val m = (tokens?.medium ?: 12).dp
        val l = (tokens?.large ?: 16).dp
        val xl = (tokens?.extraLarge ?: 28).dp
        return M3Shapes(
            extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(xs),
            small = androidx.compose.foundation.shape.RoundedCornerShape(s),
            medium = androidx.compose.foundation.shape.RoundedCornerShape(m),
            large = androidx.compose.foundation.shape.RoundedCornerShape(l),
            extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(xl)
        )
    }

    private fun mapTypography(tokens: ThemeTokens?, fontConfig: FontConfig?): Typography {
        // Minimal implementation: use default typography but swap font family if provided.
        val base = M3Typography()
        val ff = fontConfig?.fontFamily
        return if (ff != null) {
            base.copy(
                displayLarge = base.displayLarge.copy(fontFamily = ff),
                displayMedium = base.displayMedium.copy(fontFamily = ff),
                displaySmall = base.displaySmall.copy(fontFamily = ff),
                headlineLarge = base.headlineLarge.copy(fontFamily = ff),
                headlineMedium = base.headlineMedium.copy(fontFamily = ff),
                headlineSmall = base.headlineSmall.copy(fontFamily = ff),
                titleLarge = base.titleLarge.copy(fontFamily = ff),
                titleMedium = base.titleMedium.copy(fontFamily = ff),
                titleSmall = base.titleSmall.copy(fontFamily = ff),
                bodyLarge = base.bodyLarge.copy(fontFamily = ff),
                bodyMedium = base.bodyMedium.copy(fontFamily = ff),
                bodySmall = base.bodySmall.copy(fontFamily = ff),
                labelLarge = base.labelLarge.copy(fontFamily = ff),
                labelMedium = base.labelMedium.copy(fontFamily = ff),
                labelSmall = base.labelSmall.copy(fontFamily = ff)
            )
        } else {
            base
        }
    }
}