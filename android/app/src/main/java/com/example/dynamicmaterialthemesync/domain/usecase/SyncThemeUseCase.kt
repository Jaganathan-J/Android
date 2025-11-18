package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.domain.model.ThemeData
import com.example.dynamicmaterialthemesync.domain.model.ThemePreferences
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = true): Result<ThemeData> {
        return if (forceRefresh) {
            repository.fetchRemoteTheme()
        } else {
            repository.fetchRemoteTheme()
        }
    }
}

class ObserveThemePreferencesUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    operator fun invoke(): Flow<ThemePreferences> = repository.observeThemePreferences()
}

class UpdateThemePreferencesUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(preferences: ThemePreferences) {
        repository.saveThemePreferences(preferences)
    }
}