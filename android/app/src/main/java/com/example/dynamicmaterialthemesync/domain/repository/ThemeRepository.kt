package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.SyncInfo
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getThemeTokens(): Flow<ThemeTokens?>
    suspend fun syncThemeTokens(source: ThemeSource): Result<ThemeTokens>
    suspend fun getLocalThemeTokens(): ThemeTokens?
    suspend fun cacheThemeTokens(tokens: ThemeTokens)
    suspend fun getLastSyncInfo(): SyncInfo
}