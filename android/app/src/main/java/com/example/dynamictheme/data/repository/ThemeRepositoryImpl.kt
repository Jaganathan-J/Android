package com.example.dynamictheme.data.repository

import com.example.dynamictheme.data.source.local.LocalThemeDataSource
import com.example.dynamictheme.data.source.remote.RemoteThemeDataSource
import com.example.dynamictheme.domain.model.SyncInfo
import com.example.dynamictheme.domain.model.ThemeTokens
import com.example.dynamictheme.domain.model.enums.ThemeSource
import com.example.dynamictheme.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    private val remoteThemeDataSource: RemoteThemeDataSource,
    private val localThemeDataSource: LocalThemeDataSource
) : ThemeRepository {

    private val tokensFlow = MutableStateFlow<ThemeTokens?>(null)
    private var lastSync: SyncInfo = SyncInfo(null, null)

    override fun getThemeTokens(): Flow<ThemeTokens?> = tokensFlow

    override suspend fun syncThemeTokens(source: ThemeSource, remoteUrl: String?): Result<ThemeTokens> {
        return when (source) {
            ThemeSource.Remote -> {
                val url = remoteUrl ?: return Result.failure(IllegalArgumentException("Remote URL required"))
                val result = remoteThemeDataSource.fetchTheme(url)
                result.onSuccess { tokens ->
                    tokensFlow.value = tokens
                    lastSync = SyncInfo(System.currentTimeMillis(), tokens.themeVersion)
                    localThemeDataSource.cacheTheme(tokens)
                }
                result
            }
            ThemeSource.Local -> {
                val local = localThemeDataSource.loadBundledTheme()
                if (local != null) {
                    tokensFlow.value = local
                    lastSync = SyncInfo(System.currentTimeMillis(), local.themeVersion)
                    Result.success(local)
                } else {
                    Result.failure(IllegalStateException("No local theme"))
                }
            }
            ThemeSource.SystemDynamic -> {
                val cached = localThemeDataSource.loadCachedTheme()
                if (cached != null) {
                    tokensFlow.value = cached
                    lastSync = SyncInfo(System.currentTimeMillis(), cached.themeVersion)
                    Result.success(cached)
                } else {
                    Result.failure(IllegalStateException("No cached theme"))
                }
            }
        }
    }

    override suspend fun getLocalThemeTokens(): ThemeTokens? {
        return tokensFlow.value ?: localThemeDataSource.loadCachedTheme()
    }

    override suspend fun cacheThemeTokens(tokens: ThemeTokens) {
        tokensFlow.value = tokens
        localThemeDataSource.cacheTheme(tokens)
    }

    override suspend fun getLastSyncInfo(): SyncInfo = lastSync
}