package com.example.dynamictheme.domain.repository

import com.example.dynamictheme.domain.model.SyncInfo
import com.example.dynamictheme.domain.model.ThemeTokens
import com.example.dynamictheme.domain.model.enums.ThemeSource
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getThemeTokens(): Flow<ThemeTokens?>
    suspend fun syncThemeTokens(source: ThemeSource, remoteUrl: String? = null): Result<ThemeTokens>
    suspend fun getLocalThemeTokens(): ThemeTokens?
    suspend fun cacheThemeTokens(tokens: ThemeTokens)
    suspend fun getLastSyncInfo(): SyncInfo
}