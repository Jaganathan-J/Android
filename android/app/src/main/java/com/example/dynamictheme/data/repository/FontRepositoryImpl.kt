package com.example.dynamictheme.data.repository

import com.example.dynamictheme.domain.model.FontConfig
import com.example.dynamictheme.domain.model.ThemeTokens
import com.example.dynamictheme.domain.repository.FontRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FontRepositoryImpl @Inject constructor() : FontRepository {

    private var preferredFont: FontConfig? = null

    override suspend fun getPreferredFont(): FontConfig? = preferredFont

    override suspend fun resolveFontFromTheme(tokens: ThemeTokens): Result<FontConfig> {
        val typography = tokens.typography
        val family = typography?.fontFamily ?: return Result.failure(IllegalStateException("No font family"))
        val config = FontConfig(
            family = family,
            fallbacks = typography.fallback ?: emptyList(),
            variants = emptyList(),
            axes = emptyList()
        )
        preferredFont = config
        return Result.success(config)
    }

    override suspend fun fetchFontMetadata(family: String): Result<FontConfig> {
        val config = FontConfig(family = family)
        preferredFont = config
        return Result.success(config)
    }

    override suspend fun cacheFontFiles(config: FontConfig) {
        preferredFont = config
    }

    override suspend fun setPreferredFont(config: FontConfig) {
        preferredFont = config
    }
}