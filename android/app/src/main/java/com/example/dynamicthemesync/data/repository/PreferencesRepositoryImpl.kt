package com.example.dynamicthemesync.data.repository

import com.example.dynamicthemesync.data.source.preferences.ThemePreferencesDataSource
import com.example.dynamicthemesync.domain.model.ThemePreferences
import com.example.dynamicthemesync.domain.repository.PreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val dataSource: ThemePreferencesDataSource
) : PreferencesRepository {

    override fun observeThemePreferences(): Flow<ThemePreferences> = dataSource.observeThemePreferences()

    override suspend fun updateThemePreferences(preferences: ThemePreferences) {
        dataSource.updateThemePreferences(preferences)
    }

    override suspend fun clear() {
        dataSource.clear()
    }
}