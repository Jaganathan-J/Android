package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.data.local.ThemePreferencesDataStore
import com.example.dynamicmaterialthemesync.data.remote.ThemeApiService
import com.example.dynamicmaterialthemesync.domain.model.ThemeData
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val apiService: ThemeApiService,
    private val preferencesDataStore: ThemePreferencesDataStore
) : ThemeRepository {

    override suspend fun fetchRemoteTheme(): Result<ThemeData> {
        return try {
            val theme = apiService.getTheme()
            Result.success(theme)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeThemePreferences(): Flow<ThemePreferences> =
        preferencesDataStore.observeThemePreferences()

    override suspend fun saveThemePreferences(preferences: ThemePreferences) {
        preferencesDataStore.saveThemePreferences(preferences)
    }
}