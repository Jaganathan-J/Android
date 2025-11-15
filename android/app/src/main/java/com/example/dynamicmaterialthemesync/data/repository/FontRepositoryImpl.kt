package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import com.example.dynamicmaterialthemesync.domain.model.FontSource
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FontRepositoryImpl : FontRepository {

    private val preferredFont = MutableStateFlow<FontConfig?>(null)

    override fun getPreferredFont(): Flow<FontConfig?> = preferredFont

    override suspend fun resolveFontFromTheme(fontName: String?, fallbacks: List<String>?): Result<FontConfig> {
        if (fontName.isNullOrBlank()) {
            return Result.failure(IllegalArgumentException("fontName is null"))
        }
        val config = FontConfig(
            family = fontName,
            fallback = fallbacks.orEmpty(),
            source = FontSource.THEME_JSON
        )
        preferredFont.value = config
        return Result.success(config)
    }
}