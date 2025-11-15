package com.example.dynamicmaterialthemesync.data.source.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dynamicmaterialthemesync.domain.model.IconStyle
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "theme_prefs"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_NAME)

class ThemePreferencesDataSource(
    private val context: Context
) {
    private object Keys {
        val THEME_SOURCE = stringPreferencesKey("theme_source")
        val REMOTE_URL = stringPreferencesKey("remote_url")
        val LOCAL_ASSET = stringPreferencesKey("local_asset")
        val USE_DYNAMIC = booleanPreferencesKey("use_dynamic_color")
        val FONT_FAMILY = stringPreferencesKey("font_family")
        val ICON_STYLE = stringPreferencesKey("icon_style")
        val ICON_WEIGHT = longPreferencesKey("icon_weight")
        val ICON_GRADE = longPreferencesKey("icon_grade")
        val ICON_OPSZ = longPreferencesKey("icon_opsz")
        val APPLY_ON_LAUNCH = booleanPreferencesKey("apply_on_launch")
        val LAST_THEME_VERSION = stringPreferencesKey("last_theme_version")
        val LAST_SYNC_TIME = longPreferencesKey("last_sync_time")
    }

    fun observe(): Flow<ThemePreferences> {
        return context.dataStore.data.map { prefs ->
            val sourceType = when (prefs[Keys.THEME_SOURCE]) {
                "system_dynamic" -> ThemeSourceType.SYSTEM_DYNAMIC
                "remote" -> ThemeSourceType.REMOTE
                "local" -> ThemeSourceType.LOCAL
                else -> ThemeSourceType.SYSTEM_DYNAMIC
            }
            val themeSource = ThemeSource(
                type = sourceType,
                remoteUrl = prefs[Keys.REMOTE_URL],
                localAssetName = prefs[Keys.LOCAL_ASSET]
            )
            val iconStyle = when (prefs[Keys.ICON_STYLE]) {
                "filled" -> IconStyle.FILLED
                "rounded" -> IconStyle.ROUNDED
                else -> IconStyle.OUTLINED
            }
            ThemePreferences(
                themeSource = themeSource,
                useDynamicColor = prefs[Keys.USE_DYNAMIC] ?: true,
                selectedFontFamily = prefs[Keys.FONT_FAMILY],
                iconStyle = iconStyle,
                iconWeight = prefs[Keys.ICON_WEIGHT]?.toInt(),
                iconGrade = prefs[Keys.ICON_GRADE]?.toInt(),
                iconOpticalSize = prefs[Keys.ICON_OPSZ]?.toInt(),
                applyOnLaunch = prefs[Keys.APPLY_ON_LAUNCH] ?: true,
                lastThemeVersion = prefs[Keys.LAST_THEME_VERSION],
                lastSyncTimeMillis = prefs[Keys.LAST_SYNC_TIME]
            )
        }
    }

    suspend fun update(prefsIn: ThemePreferences) {
        context.dataStore.edit { prefs ->
            prefs[Keys.THEME_SOURCE] = when (prefsIn.themeSource.type) {
                ThemeSourceType.SYSTEM_DYNAMIC -> "system_dynamic"
                ThemeSourceType.REMOTE -> "remote"
                ThemeSourceType.LOCAL -> "local"
            }
            prefs[Keys.REMOTE_URL] = prefsIn.themeSource.remoteUrl ?: ""
            prefs[Keys.LOCAL_ASSET] = prefsIn.themeSource.localAssetName ?: ""
            prefs[Keys.USE_DYNAMIC] = prefsIn.useDynamicColor
            prefs[Keys.FONT_FAMILY] = prefsIn.selectedFontFamily ?: ""
            prefs[Keys.ICON_STYLE] = when (prefsIn.iconStyle) {
                IconStyle.FILLED -> "filled"
                IconStyle.OUTLINED -> "outlined"
                IconStyle.ROUNDED -> "rounded"
            }
            prefs[Keys.ICON_WEIGHT] = (prefsIn.iconWeight ?: 400).toLong()
            prefs[Keys.ICON_GRADE] = (prefsIn.iconGrade ?: 0).toLong()
            prefs[Keys.ICON_OPSZ] = (prefsIn.iconOpticalSize ?: 24).toLong()
            prefs[Keys.APPLY_ON_LAUNCH] = prefsIn.applyOnLaunch
            prefs[Keys.LAST_THEME_VERSION] = prefsIn.lastThemeVersion ?: ""
            prefs[Keys.LAST_SYNC_TIME] = prefsIn.lastSyncTimeMillis ?: 0L
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}