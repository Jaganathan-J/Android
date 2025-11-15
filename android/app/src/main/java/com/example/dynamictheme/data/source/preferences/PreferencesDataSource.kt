package com.example.dynamictheme.data.source.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dynamictheme.domain.model.ThemePreferences
import com.example.dynamictheme.domain.model.enums.IconStyle
import com.example.dynamictheme.domain.model.enums.ThemeSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val DATASTORE_NAME = "theme_prefs"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class PreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val THEME_SOURCE = stringPreferencesKey("theme_source")
        val REMOTE_URL = stringPreferencesKey("remote_url")
        val USE_DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_color")
        val SELECTED_FONT_FAMILY = stringPreferencesKey("selected_font_family")
        val SELECTED_ICON_STYLE = stringPreferencesKey("selected_icon_style")
        val ICON_WEIGHT = intPreferencesKey("icon_weight")
        val ICON_GRADE = intPreferencesKey("icon_grade")
        val ICON_OPSZ = intPreferencesKey("icon_opsz")
        val LAST_THEME_VERSION = stringPreferencesKey("last_theme_version")
        val LAST_SYNC_TIME = longPreferencesKey("last_sync_time")
        val APPLY_ON_LAUNCH = booleanPreferencesKey("apply_on_launch")
    }

    fun observePreferences(): Flow<ThemePreferences> = context.dataStore.data.map { prefs ->
        ThemePreferences(
            themeSource = when (prefs[Keys.THEME_SOURCE]) {
                ThemeSource.Remote.name -> ThemeSource.Remote
                ThemeSource.Local.name -> ThemeSource.Local
                else -> ThemeSource.SystemDynamic
            },
            remoteUrl = prefs[Keys.REMOTE_URL],
            useDynamicColor = prefs[Keys.USE_DYNAMIC_COLOR] ?: true,
            selectedFontFamily = prefs[Keys.SELECTED_FONT_FAMILY],
            selectedIconStyle = when (prefs[Keys.SELECTED_ICON_STYLE]) {
                IconStyle.Outlined.name -> IconStyle.Outlined
                IconStyle.Rounded.name -> IconStyle.Rounded
                else -> IconStyle.Filled
            },
            iconWeight = prefs[Keys.ICON_WEIGHT],
            iconGrade = prefs[Keys.ICON_GRADE],
            iconOpticalSize = prefs[Keys.ICON_OPSZ],
            lastThemeVersion = prefs[Keys.LAST_THEME_VERSION],
            lastSyncTimeMillis = prefs[Keys.LAST_SYNC_TIME],
            applyOnLaunch = prefs[Keys.APPLY_ON_LAUNCH] ?: true
        )
    }

    suspend fun updatePreferences(preferences: ThemePreferences) {
        context.dataStore.edit { prefs ->
            prefs[Keys.THEME_SOURCE] = preferences.themeSource.name
            prefs[Keys.REMOTE_URL] = preferences.remoteUrl ?: ""
            prefs[Keys.USE_DYNAMIC_COLOR] = preferences.useDynamicColor
            prefs[Keys.SELECTED_FONT_FAMILY] = preferences.selectedFontFamily ?: ""
            prefs[Keys.SELECTED_ICON_STYLE] = preferences.selectedIconStyle.name
            preferences.iconWeight?.let { prefs[Keys.ICON_WEIGHT] = it }
            preferences.iconGrade?.let { prefs[Keys.ICON_GRADE] = it }
            preferences.iconOpticalSize?.let { prefs[Keys.ICON_OPSZ] = it }
            prefs[Keys.LAST_THEME_VERSION] = preferences.lastThemeVersion ?: ""
            preferences.lastSyncTimeMillis?.let { prefs[Keys.LAST_SYNC_TIME] = it }
            prefs[Keys.APPLY_ON_LAUNCH] = preferences.applyOnLaunch
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}