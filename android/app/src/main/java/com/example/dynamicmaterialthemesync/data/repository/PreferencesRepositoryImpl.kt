package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.data.source.preferences.ThemePreferencesDataSource
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val dataSource: ThemePreferencesDataSource
) : PreferencesRepository {

    override fun observeThemePreferences(): Flow<ThemePreferences> = dataSource.observePreferences()

    override suspend fun updateThemePreferences(update: (ThemePreferences) -> ThemePreferences) {
        dataSource.updatePreferences(update)
    }

    override suspend fun clear() {
        dataSource.clear()
    }
}