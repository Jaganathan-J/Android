package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import com.example.dynamicmaterialthemesync.domain.model.FontOption
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import kotlinx.coroutines.flow.Flow

interface FontRepository {
    fun getPreferredFont(): Flow<FontConfig?>
    suspend fun resolveFontFromTheme(tokens: ThemeTokens?): Result<FontConfig>
    suspend fun fetchFontMetadata(family: String): Result<FontConfig>
    suspend fun cacheFontFiles(config: FontConfig)
    suspend fun getAvailableFonts(tokens: ThemeTokens?): List<FontOption>
    suspend fun setPreferredFont(familyName: String)
}