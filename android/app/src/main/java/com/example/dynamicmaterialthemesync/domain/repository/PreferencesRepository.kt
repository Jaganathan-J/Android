package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun observeThemePreferences(): Flow<ThemePreferences>
    suspend fun updateThemePreferences(prefs: ThemePreferences)
    suspend fun clear()
}