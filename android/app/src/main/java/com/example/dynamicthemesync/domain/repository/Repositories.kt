package com.example.dynamicthemesync.domain.repository

import com.example.dynamicthemesync.domain.model.IconStyle
import com.example.dynamicthemesync.domain.model.IconStyleConfig
import com.example.dynamicthemesync.domain.model.FontConfig
import com.example.dynamicthemesync.domain.model.SyncInfo
import com.example.dynamicthemesync.domain.model.ThemePreferences
import com.example.dynamicthemesync.domain.model.ThemeSourceType
import com.example.dynamicthemesync.domain.model.ThemeTokens
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getThemeTokens(): Flow<ThemeTokens?>
    suspend fun syncThemeTokens(source: ThemeSourceType, remoteUrl: String?): Result<ThemeTokens>
    suspend fun getLocalThemeTokens(): ThemeTokens?
    suspend fun cacheThemeTokens(tokens: ThemeTokens)
    suspend fun getLastSyncInfo(): SyncInfo
}

interface FontRepository {
    fun getPreferredFont(): Flow<FontConfig?>
    suspend fun resolveFontFromTheme(tokens: ThemeTokens): Result<FontConfig>
}

interface IconRepository {
    fun getIconStyle(): Flow<IconStyleConfig>
    suspend fun setIconStyle(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?)
    fun supportedStyles(): List<IconStyle>
}

interface PreferencesRepository {
    fun observeThemePreferences(): Flow<ThemePreferences>
    suspend fun updateThemePreferences(preferences: ThemePreferences)
    suspend fun clear()
}

interface DynamicColorProvider {
    fun isSupported(): Boolean
    fun getLightScheme(): androidx.compose.material3.ColorScheme
    fun getDarkScheme(): androidx.compose.material3.ColorScheme
}