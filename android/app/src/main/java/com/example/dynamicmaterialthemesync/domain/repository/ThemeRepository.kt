package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.core.theme.ThemeMode
import com.example.dynamicmaterialthemesync.core.util.Result
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    /**
     * Emits the current active theme tokens, including updates when
     * remote or local sources change.
     */
    fun observeThemeTokens(): Flow<ThemeTokens>

    /**
     * Fetch latest tokens from remote Figma JSON endpoint and persist them.
     */
    suspend fun refreshThemeTokens(): Result<Unit>

    /**
     * Returns the current selected theme mode (system/light/dark).
     */
    fun observeThemeMode(): Flow<ThemeMode>

    /**
     * Persist the selected theme mode.
     */
    suspend fun setThemeMode(mode: ThemeMode)
}