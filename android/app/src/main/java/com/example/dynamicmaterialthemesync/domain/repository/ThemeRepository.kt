package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.ThemeData
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    suspend fun fetchRemoteTheme(): Result<ThemeData>
    fun observeThemePreferences(): Flow<ThemePreferences>
    suspend fun saveThemePreferences(preferences: ThemePreferences)
}