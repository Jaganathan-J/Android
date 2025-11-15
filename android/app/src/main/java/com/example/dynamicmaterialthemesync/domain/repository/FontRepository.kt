package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import kotlinx.coroutines.flow.Flow

interface FontRepository {
    fun getPreferredFont(): Flow<FontConfig?>
    suspend fun resolveFontFromTheme(fontName: String?, fallbacks: List<String>?): Result<FontConfig>
}