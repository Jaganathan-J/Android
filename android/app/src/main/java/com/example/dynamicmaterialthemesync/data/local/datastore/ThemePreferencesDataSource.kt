package com.example.dynamicmaterialthemesync.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dynamicmaterialthemesync.core.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val DATASTORE_NAME = "theme_preferences"

private val Context.themeDataStore by preferencesDataStore(name = DATASTORE_NAME)

@Singleton
class ThemePreferencesDataSource @Inject constructor(
    private val context: Context
) {

    private object Keys {
        val THEME_MODE = intPreferencesKey("theme_mode")
        val THEME_VERSION = stringPreferencesKey("theme_version")
    }

    fun observeThemeMode(): Flow<ThemeMode> = context.themeDataStore.data
        .map { prefs ->
            when (prefs[Keys.THEME_MODE] ?: 0) {
                1 -> ThemeMode.LIGHT
                2 -> ThemeMode.DARK
                else -> ThemeMode.SYSTEM
            }
        }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.themeDataStore.edit { prefs: Preferences ->
            prefs[Keys.THEME_MODE] = when (mode) {
                ThemeMode.SYSTEM -> 0
                ThemeMode.LIGHT -> 1
                ThemeMode.DARK -> 2
            }
        }
    }

    fun observeThemeVersion(): Flow<String?> = context.themeDataStore.data
        .map { prefs -> prefs[Keys.THEME_VERSION] }

    suspend fun setThemeVersion(version: String?) {
        context.themeDataStore.edit { prefs ->
            if (version == null) {
                prefs.remove(Keys.THEME_VERSION)
            } else {
                prefs[Keys.THEME_VERSION] = version
            }
        }
    }
}