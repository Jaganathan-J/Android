package com.example.dynamicmaterialthemesync.data.source.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val DATASTORE_NAME = "theme_prefs"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

@Singleton
class ThemePreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val KEY_THEME_SOURCE = stringPreferencesKey("theme_source")
    private val KEY_REMOTE_URL = stringPreferencesKey("remote_url")
    private val KEY_USE_DYNAMIC = booleanPreferencesKey("use_dynamic_color")
    private val KEY_APPLY_ON_LAUNCH = booleanPreferencesKey("apply_on_launch")
    private val KEY_FONT_FAMILY = stringPreferencesKey("selected_font_family")
    private val KEY_ICON_STYLE = stringPreferencesKey("selected_icon_style")
    private val KEY_ICON_WEIGHT = longPreferencesKey("icon_weight")
    private val KEY_ICON_GRADE = longPreferencesKey("icon_grade")
    private val KEY_ICON_OPSZ = longPreferencesKey("icon_opsz")
    private val KEY_LAST_THEME_VERSION = stringPreferencesKey("last_theme_version")
    private val KEY_LAST_SYNC_TIME = longPreferencesKey("last_sync_time")

    fun observePreferences(): Flow<ThemePreferences> {
        return context.dataStore.data.map { prefs ->
            val sourceName = prefs[KEY_THEME_SOURCE]
            val source = when (sourceName) {
                ThemeSource.REMOTE.name -> ThemeSource.REMOTE
                ThemeSource.LOCAL.name -> ThemeSource.LOCAL
                else -> ThemeSource.SYSTEM_DYNAMIC
            }
            val iconStyleStr = prefs[KEY_ICON_STYLE]
            val iconStyle = when (iconStyleStr) {
                IconStyle.OUTLINED.name -> IconStyle.OUTLINED
                IconStyle.ROUNDED.name -> IconStyle.ROUNDED
                else -> IconStyle.FILLED
            }
            ThemePreferences(
                themeSource = source,
                remoteUrl = prefs[KEY_REMOTE_URL],
                useDynamicColor = prefs[KEY_USE_DYNAMIC] ?: true,
                applyOnLaunch = prefs[KEY_APPLY_ON_LAUNCH] ?: true,
                selectedFontFamily = prefs[KEY_FONT_FAMILY],
                selectedIconStyle = iconStyle,
                iconWeight = prefs[KEY_ICON_WEIGHT]?.toInt(),
                iconGrade = prefs[KEY_ICON_GRADE]?.toInt(),
                iconOpticalSize = prefs[KEY_ICON_OPSZ]?.toInt(),
                lastThemeVersion = prefs[KEY_LAST_THEME_VERSION],
                lastSyncTimeMillis = prefs[KEY_LAST_SYNC_TIME]
            )
        }
    }

    suspend fun updatePreferences(update: (ThemePreferences) -> ThemePreferences) {
        context.dataStore.edit { prefs ->
            val current = ThemePreferences(
                themeSource = when (prefs[KEY_THEME_SOURCE]) {
                    ThemeSource.REMOTE.name -> ThemeSource.REMOTE
                    ThemeSource.LOCAL.name -> ThemeSource.LOCAL
                    else -> ThemeSource.SYSTEM_DYNAMIC
                },
                remoteUrl = prefs[KEY_REMOTE_URL],
                useDynamicColor = prefs[KEY_USE_DYNAMIC] ?: true,
                applyOnLaunch = prefs[KEY_APPLY_ON_LAUNCH] ?: true,
                selectedFontFamily = prefs[KEY_FONT_FAMILY],
                selectedIconStyle = when (prefs[KEY_ICON_STYLE]) {
                    IconStyle.OUTLINED.name -> IconStyle.OUTLINED
                    IconStyle.ROUNDED.name -> IconStyle.ROUNDED
                    else -> IconStyle.FILLED
                },
                iconWeight = prefs[KEY_ICON_WEIGHT]?.toInt(),
                iconGrade = prefs[KEY_ICON_GRADE]?.toInt(),
                iconOpticalSize = prefs[KEY_ICON_OPSZ]?.toInt(),
                lastThemeVersion = prefs[KEY_LAST_THEME_VERSION],
                lastSyncTimeMillis = prefs[KEY_LAST_SYNC_TIME]
            )
            val newPrefs = update(current)
            prefs[KEY_THEME_SOURCE] = newPrefs.themeSource.name
            if (newPrefs.remoteUrl != null) prefs[KEY_REMOTE_URL] = newPrefs.remoteUrl else prefs.remove(KEY_REMOTE_URL)
            prefs[KEY_USE_DYNAMIC] = newPrefs.useDynamicColor
            prefs[KEY_APPLY_ON_LAUNCH] = newPrefs.applyOnLaunch
            if (newPrefs.selectedFontFamily != null) prefs[KEY_FONT_FAMILY] = newPrefs.selectedFontFamily else prefs.remove(KEY_FONT_FAMILY)
            prefs[KEY_ICON_STYLE] = newPrefs.selectedIconStyle.name
            newPrefs.iconWeight?.let { prefs[KEY_ICON_WEIGHT] = it.toLong() } ?: prefs.remove(KEY_ICON_WEIGHT)
            newPrefs.iconGrade?.let { prefs[KEY_ICON_GRADE] = it.toLong() } ?: prefs.remove(KEY_ICON_GRADE)
            newPrefs.iconOpticalSize?.let { prefs[KEY_ICON_OPSZ] = it.toLong() } ?: prefs.remove(KEY_ICON_OPSZ)
            newPrefs.lastThemeVersion?.let { prefs[KEY_LAST_THEME_VERSION] = it } ?: prefs.remove(KEY_LAST_THEME_VERSION)
            newPrefs.lastSyncTimeMillis?.let { prefs[KEY_LAST_SYNC_TIME] = it } ?: prefs.remove(KEY_LAST_SYNC_TIME)
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}