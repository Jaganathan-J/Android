package com.example.dynamicmaterialthemesync.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dynamicmaterialthemesync.domain.model.ThemeMode
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val THEME_PREFERENCES_NAME = "theme_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = THEME_PREFERENCES_NAME)

class ThemePreferencesDataStore(private val context: Context) {

    private val KEY_THEME_MODE = intPreferencesKey("theme_mode")
    private val KEY_USE_DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_color")
    private val KEY_USE_REMOTE_THEME = booleanPreferencesKey("use_remote_theme")

    fun observeThemePreferences(): Flow<ThemePreferences> =
        context.dataStore.data.map { prefs ->
            val modeOrdinal = prefs[KEY_THEME_MODE] ?: ThemeMode.SYSTEM.ordinal
            val mode = ThemeMode.values().getOrElse(modeOrdinal) { ThemeMode.SYSTEM }
            ThemePreferences(
                themeMode = mode,
                useDynamicColor = prefs[KEY_USE_DYNAMIC_COLOR] ?: true,
                useRemoteTheme = prefs[KEY_USE_REMOTE_THEME] ?: true
            )
        }

    suspend fun saveThemePreferences(preferences: ThemePreferences) {
        context.dataStore.edit { prefs ->
            prefs[KEY_THEME_MODE] = preferences.themeMode.ordinal
            prefs[KEY_USE_DYNAMIC_COLOR] = preferences.useDynamicColor
            prefs[KEY_USE_REMOTE_THEME] = preferences.useRemoteTheme
        }
    }
}