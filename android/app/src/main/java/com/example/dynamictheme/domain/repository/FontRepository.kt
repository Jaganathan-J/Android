package com.example.dynamictheme.domain.repository

import com.example.dynamictheme.domain.model.FontConfig
import com.example.dynamictheme.domain.model.ThemeTokens

interface FontRepository {
    suspend fun getPreferredFont(): FontConfig?
    suspend fun resolveFontFromTheme(tokens: ThemeTokens): Result<FontConfig>
    suspend fun fetchFontMetadata(family: String): Result<FontConfig>
    suspend fun cacheFontFiles(config: FontConfig)
    suspend fun setPreferredFont(config: FontConfig)
}