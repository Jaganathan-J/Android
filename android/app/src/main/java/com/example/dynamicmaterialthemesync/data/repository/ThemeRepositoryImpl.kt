package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.data.mapper.toDomain
import com.example.dynamicmaterialthemesync.data.source.local.LocalThemeDataSource
import com.example.dynamicmaterialthemesync.data.source.remote.ThemeApiService
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    private val apiService: ThemeApiService,
    private val localThemeDataSource: LocalThemeDataSource
) : ThemeRepository {

    private val tokensFlow = MutableStateFlow<ThemeTokens?>(null)
    private val syncInfoFlow = MutableStateFlow<Pair<String?, Long?>>(null to null)

    override fun getThemeTokens(): Flow<ThemeTokens?> = tokensFlow.asStateFlow()

    override suspend fun syncThemeTokens(preferences: ThemePreferences): Result<ThemeTokens> {
        return runCatching {
            val sourceUrl = preferences.remoteUrl ?: DEFAULT_REMOTE_URL
            val response = apiService.fetchTheme(sourceUrl)
            val domain = response.toDomain()
            tokensFlow.value = domain
            syncInfoFlow.value = domain.themeVersion to System.currentTimeMillis()
            localThemeDataSource.cacheTheme(response)
            domain
        }.recoverCatching {
            val cached = localThemeDataSource.loadCachedTheme() ?: localThemeDataSource.loadBundledTheme()
                ?: throw it
            val domain = cached.toDomain()
            tokensFlow.value = domain
            domain
        }
    }

    override suspend fun getLocalThemeTokens(): ThemeTokens? {
        val cached = localThemeDataSource.loadCachedTheme() ?: localThemeDataSource.loadBundledTheme()
        return cached?.toDomain()
    }

    override suspend fun cacheThemeTokens(tokens: ThemeTokens) {
        // Already cached JSON in syncThemeTokens; nothing extra here for now.
    }

    override fun getLastSyncInfo(): Flow<Pair<String?, Long?>> = syncInfoFlow.asStateFlow()

    companion object {
        private const val DEFAULT_REMOTE_URL = "https://example.com/themes/material3.json"
    }
}