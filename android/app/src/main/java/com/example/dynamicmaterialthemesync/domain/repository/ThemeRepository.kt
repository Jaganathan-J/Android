package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getThemeTokens(): Flow<ThemeTokens?>
    suspend fun syncThemeTokens(preferences: ThemePreferences): Result<ThemeTokens>
    suspend fun getLocalThemeTokens(): ThemeTokens?
    suspend fun cacheThemeTokens(tokens: ThemeTokens)
    fun getLastSyncInfo(): Flow<Pair<String?, Long?>>
}