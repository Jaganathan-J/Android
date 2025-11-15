package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.data.source.preferences.ThemePreferencesDataSource
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(
    private val dataSource: ThemePreferencesDataSource
) : PreferencesRepository {
    override fun observeThemePreferences(): Flow<ThemePreferences> = dataSource.observeThemePreferences()

    override suspend fun updateThemePreferences(prefs: ThemePreferences) {
        dataSource.updateThemePreferences(prefs)
    }

    override suspend fun clear() {
        dataSource.clear()
    }
}