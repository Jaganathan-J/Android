package com.example.dynamictheme.data.repository

import com.example.dynamictheme.data.source.preferences.PreferencesDataSource
import com.example.dynamictheme.domain.model.ThemePreferences
import com.example.dynamictheme.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val dataSource: PreferencesDataSource
) : PreferencesRepository {

    override fun observeThemePreferences(): Flow<ThemePreferences> = dataSource.observePreferences()

    override suspend fun updateThemePreferences(prefs: ThemePreferences) {
        dataSource.updatePreferences(prefs)
    }

    override suspend fun clear() {
        dataSource.clear()
    }
}