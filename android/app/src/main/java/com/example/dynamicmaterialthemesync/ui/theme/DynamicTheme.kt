package com.example.dynamicmaterialthemesync.ui.theme

import android.os.Build
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.example.dynamicmaterialthemesync.domain.model.ThemeMode
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences

private val LightDefaultColorScheme = lightColorScheme()
private val DarkDefaultColorScheme = darkColorScheme()

@Immutable
data class ThemeState(
    val preferences: ThemePreferences = ThemePreferences(),
    val remoteThemeLight: ColorScheme? = null,
    val remoteThemeDark: ColorScheme? = null
)

@Composable
fun DynamicTheme(
    themeState: ThemeState,
    content: @Composable (ColorScheme) -> Unit
) {
    val context = LocalContext.current
    val systemDark = isSystemInDarkTheme()

    val useDark = when (themeState.preferences.themeMode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val dynamicSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val dynamicColorScheme: ColorScheme? = if (themeState.preferences.useDynamicColor && dynamicSupported) {
        if (useDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        null
    }

    val remoteColorScheme: ColorScheme? = if (themeState.preferences.useRemoteTheme) {
        if (useDark) themeState.remoteThemeDark else themeState.remoteThemeLight
    } else {
        null
    }

    val targetScheme: ColorScheme = dynamicColorScheme
        ?: remoteColorScheme
        ?: if (useDark) DarkDefaultColorScheme else LightDefaultColorScheme

    Crossfade(targetState = targetScheme, label = "theme_crossfade") { scheme ->
        MaterialTheme(
            colorScheme = scheme,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = { content(scheme) }
        )
    }
}