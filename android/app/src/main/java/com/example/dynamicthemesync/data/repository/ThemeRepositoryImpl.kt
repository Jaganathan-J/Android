package com.example.dynamicthemesync.data.repository

import com.example.dynamicthemesync.data.mapper.ThemeTokensDto
import com.example.dynamicthemesync.data.mapper.toDomain
import com.example.dynamicthemesync.data.source.local.LocalThemeDataSource
import com.example.dynamicthemesync.data.source.preferences.ThemePreferencesDataSource
import com.example.dynamicthemesync.data.source.remote.RemoteThemeService
import com.example.dynamicthemesync.domain.model.SyncInfo
import com.example.dynamicthemesync.domain.model.ThemePreferences
import com.example.dynamicthemesync.domain.model.ThemeSourceType
import com.example.dynamicthemesync.domain.model.ThemeTokens
import com.example.dynamicthemesync.domain.repository.ThemeRepository
import com.example.dynamicthemesync.domain.usecase.ValidateThemeJsonUseCase
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    private val remoteService: RemoteThemeService,
    private val localThemeDataSource: LocalThemeDataSource,
    private val preferencesDataSource: ThemePreferencesDataSource,
    private val validateThemeJsonUseCase: ValidateThemeJsonUseCase
) : ThemeRepository {

    private val themeTokensFlow = MutableStateFlow<ThemeTokens?>(null)

    override fun getThemeTokens(): Flow<ThemeTokens?> = themeTokensFlow

    override suspend fun syncThemeTokens(source: ThemeSourceType, remoteUrl: String?): Result<ThemeTokens> {
        return runCatching {
            val dto: ThemeTokensDto = when (source) {
                ThemeSourceType.REMOTE -> {
                    val url = remoteUrl ?: throw IllegalArgumentException("Remote URL is required")
                    require(url.startsWith("https://")) { "Only HTTPS sources allowed" }
                    remoteService.fetchThemeJson(url)
                }
                ThemeSourceType.LOCAL, ThemeSourceType.SYSTEM_DYNAMIC -> {
                    localThemeDataSource.loadBundledTheme()
                        ?: throw IllegalStateException("No bundled theme available")
                }
            }
            val tokens = dto.toDomain()
            validateThemeJsonUseCase(tokens).getOrThrow()
            themeTokensFlow.value = tokens
            tokens
        }
    }

    override suspend fun getLocalThemeTokens(): ThemeTokens? {
        return localThemeDataSource.loadBundledTheme()?.toDomain()
    }

    override suspend fun cacheThemeTokens(tokens: ThemeTokens) {
        themeTokensFlow.value = tokens
        val currentPrefs: ThemePreferences = preferencesDataSource.observeThemePreferences().first()
        val updated = currentPrefs.copy(
            lastThemeVersion = tokens.themeVersion,
            lastSyncTimeMillis = System.currentTimeMillis()
        )
        preferencesDataSource.updateThemePreferences(updated)
    }

    override suspend fun getLastSyncInfo(): SyncInfo {
        val prefs = preferencesDataSource.observeThemePreferences().first()
        return SyncInfo(
            lastSyncTimeMillis = prefs.lastSyncTimeMillis,
            themeVersion = prefs.lastThemeVersion
        )
    }
}