package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.data.mapper.ThemeDtoMapper
import com.example.dynamicmaterialthemesync.data.source.local.LocalThemeDataSource
import com.example.dynamicmaterialthemesync.data.source.remote.ThemeApiService
import com.example.dynamicmaterialthemesync.domain.model.SyncInfo
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class ThemeRepositoryImpl(
    private val apiService: ThemeApiService,
    private val localDataSource: LocalThemeDataSource
) : ThemeRepository {

    private val tokensFlow = MutableStateFlow<ThemeTokens?>(null)
    private var lastSyncInfo: SyncInfo = SyncInfo(null, null)

    override fun getThemeTokens(): Flow<ThemeTokens?> = tokensFlow

    override suspend fun syncThemeTokens(source: ThemeSource): Result<ThemeTokens> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val dto = when (source.type) {
                    com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType.REMOTE -> {
                        val url = requireNotNull(source.remoteUrl) { "Remote URL required" }
                        apiService.fetchTheme(url)
                    }
                    com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType.LOCAL -> {
                        val asset = requireNotNull(source.localAssetName) { "Local asset name required" }
                        localDataSource.loadFromAsset(asset) ?: throw IllegalStateException("Asset not found")
                    }
                    com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType.SYSTEM_DYNAMIC -> {
                        throw IllegalArgumentException("System dynamic has no JSON source")
                    }
                }
                ThemeDtoMapper.map(dto).also { tokens ->
                    tokensFlow.value = tokens
                    lastSyncInfo = SyncInfo(
                        lastSyncTimeMillis = System.currentTimeMillis(),
                        lastThemeVersion = tokens.themeVersion
                    )
                }
            }
        }
    }

    override suspend fun getLocalThemeTokens(): ThemeTokens? {
        return tokensFlow.value
    }

    override suspend fun cacheThemeTokens(tokens: ThemeTokens) {
        tokensFlow.value = tokens
    }

    override suspend fun getLastSyncInfo(): SyncInfo = lastSyncInfo
}