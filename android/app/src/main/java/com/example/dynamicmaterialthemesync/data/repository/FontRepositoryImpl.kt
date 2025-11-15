package com.example.dynamicmaterialthemesync.data.repository

import androidx.compose.ui.text.font.FontFamily
import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import com.example.dynamicmaterialthemesync.domain.model.FontOption
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FontRepositoryImpl @Inject constructor() : FontRepository {

    private val preferredFontFlow = MutableStateFlow<FontConfig?>(null)

    override fun getPreferredFont(): Flow<FontConfig?> = preferredFontFlow.asStateFlow()

    override suspend fun resolveFontFromTheme(tokens: ThemeTokens?): Result<FontConfig> {
        return runCatching {
            val familyName = tokens?.typography?.fontFamily ?: DEFAULT_FONT_FAMILY
            FontConfig(
                familyName = familyName,
                fontFamily = FontFamily.SansSerif,
                isVariable = false
            )
        }
    }

    override suspend fun fetchFontMetadata(family: String): Result<FontConfig> {
        return runCatching {
            FontConfig(
                familyName = family,
                fontFamily = FontFamily.SansSerif,
                isVariable = false
            )
        }
    }

    override suspend fun cacheFontFiles(config: FontConfig) {
        // No-op stub; could download and cache font files.
    }

    override suspend fun getAvailableFonts(tokens: ThemeTokens?): List<FontOption> {
        val themeFamily = tokens?.typography?.fontFamily
        val list = mutableListOf<FontOption>()
        if (themeFamily != null) {
            list += FontOption(name = themeFamily, source = "Theme JSON", isDownloaded = false)
        }
        list += FontOption(name = DEFAULT_FONT_FAMILY, source = "System", isDownloaded = true)
        return list
    }

    override suspend fun setPreferredFont(familyName: String) {
        preferredFontFlow.value = FontConfig(
            familyName = familyName,
            fontFamily = FontFamily.SansSerif,
            isVariable = false
        )
    }

    companion object {
        private const val DEFAULT_FONT_FAMILY = "Roboto"
    }
}