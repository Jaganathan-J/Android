package com.example.dynamicmaterialthemesync.data.source.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dynamicmaterialthemesync.domain.model.IconAxes
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "theme_prefs"

private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

class ThemePreferencesDataSource(private val context: Context) {

    private object Keys {
        val THEME_SOURCE = stringPreferencesKey("theme_source")
        val REMOTE_URL = stringPreferencesKey("remote_url")
        val LOCAL_ASSET = stringPreferencesKey("local_asset")
        val USE_DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_color")
        val SELECTED_FONT_FAMILY = stringPreferencesKey("selected_font_family")
        val SELECTED_ICON_STYLE = stringPreferencesKey("selected_icon_style")
        val ICON_WEIGHT = longPreferencesKey("icon_weight")
        val ICON_GRADE = longPreferencesKey("icon_grade")
        val ICON_OPSZ = longPreferencesKey("icon_opsz")
        val APPLY_ON_LAUNCH = booleanPreferencesKey("apply_on_launch")
        val LAST_THEME_VERSION = stringPreferencesKey("last_theme_version")
        val LAST_SYNC_TIME = longPreferencesKey("last_sync_time")
    }

    fun observeThemePreferences(): Flow<ThemePreferences> {
        return context.dataStore.data.map { prefs ->
            val sourceType = when (prefs[Keys.THEME_SOURCE]) {
                "remote" -> ThemeSourceType.REMOTE
                "local" -> ThemeSourceType.LOCAL
                else -> ThemeSourceType.SYSTEM_DYNAMIC
            }
            val themeSource = ThemeSource(
                type = sourceType,
                remoteUrl = prefs[Keys.REMOTE_URL],
                localAssetName = prefs[Keys.LOCAL_ASSET]
            )
            ThemePreferences(
                themeSource = themeSource,
                useDynamicColor = prefs[Keys.USE_DYNAMIC_COLOR] ?: true,
                selectedFontFamily = prefs[Keys.SELECTED_FONT_FAMILY],
                selectedIconStyle = when (prefs[Keys.SELECTED_ICON_STYLE]) {
                    "outlined" -> IconStyle.OUTLINED
                    "rounded" -> IconStyle.ROUNDED
                    else -> IconStyle.FILLED
                },
                iconAxes = IconAxes(
                    weight = (prefs[Keys.ICON_WEIGHT] ?: 400L).toInt(),
                    grade = (prefs[Keys.ICON_GRADE] ?: 0L).toInt(),
                    opticalSize = (prefs[Keys.ICON_OPSZ] ?: 24L).toInt()
                ),
                applyOnLaunch = prefs[Keys.APPLY_ON_LAUNCH] ?: true,
                lastThemeVersion = prefs[Keys.LAST_THEME_VERSION],
                lastSyncTimeMillis = prefs[Keys.LAST_SYNC_TIME]
            )
        }
    }

    suspend fun updateThemePreferences(prefs: ThemePreferences) {
        context.dataStore.edit { store ->
            store[Keys.THEME_SOURCE] = when (prefs.themeSource.type) {
                ThemeSourceType.SYSTEM_DYNAMIC -> "system"
                ThemeSourceType.REMOTE -> "remote"
                ThemeSourceType.LOCAL -> "local"
            }
            store[Keys.REMOTE_URL] = prefs.themeSource.remoteUrl ?: ""
            store[Keys.LOCAL_ASSET] = prefs.themeSource.localAssetName ?: ""
            store[Keys.USE_DYNAMIC_COLOR] = prefs.useDynamicColor
            store[Keys.SELECTED_FONT_FAMILY] = prefs.selectedFontFamily ?: ""
            store[Keys.SELECTED_ICON_STYLE] = when (prefs.selectedIconStyle) {
                IconStyle.OUTLINED -> "outlined"
                IconStyle.ROUNDED -> "rounded"
                IconStyle.FILLED -> "filled"
            }
            store[Keys.ICON_WEIGHT] = prefs.iconAxes.weight.toLong()
            store[Keys.ICON_GRADE] = prefs.iconAxes.grade.toLong()
            store[Keys.ICON_OPSZ] = prefs.iconAxes.opticalSize.toLong()
            store[Keys.APPLY_ON_LAUNCH] = prefs.applyOnLaunch
            store[Keys.LAST_THEME_VERSION] = prefs.lastThemeVersion ?: ""
            store[Keys.LAST_SYNC_TIME] = prefs.lastSyncTimeMillis ?: 0L
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}