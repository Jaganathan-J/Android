package com.example.dynamicthemesync.data.source.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dynamicthemesync.domain.model.IconStyle
import com.example.dynamicthemesync.domain.model.ThemePreferences
import com.example.dynamicthemesync.domain.model.ThemeSourceType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "theme_prefs"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class ThemePreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val KEY_THEME_SOURCE = stringPreferencesKey("theme_source")
    private val KEY_REMOTE_URL = stringPreferencesKey("remote_url")
    private val KEY_USE_DYNAMIC = booleanPreferencesKey("use_dynamic_color")
    private val KEY_FONT_FAMILY = stringPreferencesKey("selected_font_family")
    private val KEY_ICON_STYLE = stringPreferencesKey("selected_icon_style")
    private val KEY_ICON_WEIGHT = stringPreferencesKey("icon_weight")
    private val KEY_ICON_GRADE = stringPreferencesKey("icon_grade")
    private val KEY_ICON_OPSZ = stringPreferencesKey("icon_opsz")
    private val KEY_LAST_THEME_VERSION = stringPreferencesKey("last_theme_version")
    private val KEY_LAST_SYNC = longPreferencesKey("last_sync_time")

    fun observeThemePreferences(): Flow<ThemePreferences> = context.dataStore.data.map { prefs ->
        val sourceString = prefs[KEY_THEME_SOURCE] ?: ThemeSourceType.SYSTEM_DYNAMIC.name
        val source = runCatching { ThemeSourceType.valueOf(sourceString) }.getOrDefault(ThemeSourceType.SYSTEM_DYNAMIC)
        val iconStyleString = prefs[KEY_ICON_STYLE] ?: IconStyle.OUTLINED.name
        val iconStyle = runCatching { IconStyle.valueOf(iconStyleString) }.getOrDefault(IconStyle.OUTLINED)
        ThemePreferences(
            themeSource = source,
            remoteUrl = prefs[KEY_REMOTE_URL],
            useDynamicColor = prefs[KEY_USE_DYNAMIC] ?: true,
            selectedFontFamily = prefs[KEY_FONT_FAMILY],
            selectedIconStyle = iconStyle,
            iconWeight = prefs[KEY_ICON_WEIGHT]?.toIntOrNull(),
            iconGrade = prefs[KEY_ICON_GRADE]?.toIntOrNull(),
            iconOpticalSize = prefs[KEY_ICON_OPSZ]?.toIntOrNull(),
            lastThemeVersion = prefs[KEY_LAST_THEME_VERSION],
            lastSyncTimeMillis = prefs[KEY_LAST_SYNC]
        )
    }

    suspend fun updateThemePreferences(preferences: ThemePreferences) {
        context.dataStore.edit { prefs ->
            prefs[KEY_THEME_SOURCE] = preferences.themeSource.name
            prefs[KEY_REMOTE_URL] = preferences.remoteUrl ?: ""
            prefs[KEY_USE_DYNAMIC] = preferences.useDynamicColor
            prefs[KEY_FONT_FAMILY] = preferences.selectedFontFamily ?: ""
            prefs[KEY_ICON_STYLE] = preferences.selectedIconStyle.name
            prefs[KEY_ICON_WEIGHT] = preferences.iconWeight?.toString() ?: ""
            prefs[KEY_ICON_GRADE] = preferences.iconGrade?.toString() ?: ""
            prefs[KEY_ICON_OPSZ] = preferences.iconOpticalSize?.toString() ?: ""
            prefs[KEY_LAST_THEME_VERSION] = preferences.lastThemeVersion ?: ""
            prefs[KEY_LAST_SYNC] = preferences.lastSyncTimeMillis ?: 0L
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}