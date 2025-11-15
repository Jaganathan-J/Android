package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.core.theme.ThemeMode
import com.example.dynamicmaterialthemesync.core.util.Result
import com.example.dynamicmaterialthemesync.data.local.datastore.ThemePreferencesDataSource
import com.example.dynamicmaterialthemesync.data.mapper.toDomain
import com.example.dynamicmaterialthemesync.data.remote.ThemeApiService
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    private val api: ThemeApiService,
    private val preferences: ThemePreferencesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ThemeRepository {

    private val inMemoryTokens = MutableStateFlow<ThemeTokens>(createFallbackTokens())

    override fun observeThemeTokens(): Flow<ThemeTokens> = inMemoryTokens.asStateFlow()

    override suspend fun refreshThemeTokens(): Result<Unit> = withContext(ioDispatcher) {
        return@withContext try {
            val remote = api.getThemeTokens()
            val domain = remote.toDomain()
            inMemoryTokens.value = domain
            preferences.setThemeVersion(domain.version)
            Result.Success(Unit)
        } catch (t: Throwable) {
            Result.Error(t)
        }
    }

    override fun observeThemeMode(): Flow<ThemeMode> = preferences.observeThemeMode()

    override suspend fun setThemeMode(mode: ThemeMode) {
        withContext(ioDispatcher) {
            preferences.setThemeMode(mode)
        }
    }

    private fun createFallbackTokens(): ThemeTokens {
        // Simple hard-coded Material3-like baseline as fallback when remote fails.
        return ThemeTokens(
            colorTokens = emptyList(),
            typographyTokens = emptyList(),
            shapeTokens = emptyList(),
            elevationTokens = emptyList(),
            version = "fallback"
        )
    }
}