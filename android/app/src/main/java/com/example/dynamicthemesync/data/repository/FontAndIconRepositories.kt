package com.example.dynamicthemesync.data.repository

import com.example.dynamicthemesync.domain.model.FontConfig
import com.example.dynamicthemesync.domain.model.IconStyle
import com.example.dynamicthemesync.domain.model.IconStyleConfig
import com.example.dynamicthemesync.domain.model.ThemeTokens
import com.example.dynamicthemesync.domain.repository.FontRepository
import com.example.dynamicthemesync.domain.repository.IconRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Singleton
class FontRepositoryImpl @Inject constructor() : FontRepository {

    private val fontFlow = MutableStateFlow<FontConfig?>(null)

    override fun getPreferredFont(): Flow<FontConfig?> = fontFlow

    override suspend fun resolveFontFromTheme(tokens: ThemeTokens): Result<FontConfig> {
        val family = tokens.typography?.fontFamily ?: "Roboto"
        val fallbacks = tokens.typography?.fallback ?: listOf("Roboto", "Noto Sans")
        val config = FontConfig(family = family, fallbacks = fallbacks)
        fontFlow.value = config
        return Result.success(config)
    }
}

@Singleton
class IconRepositoryImpl @Inject constructor() : IconRepository {

    private val flow = MutableStateFlow(IconStyleConfig(IconStyle.OUTLINED, 400, 0, 24))

    override fun getIconStyle(): Flow<IconStyleConfig> = flow

    override suspend fun setIconStyle(style: IconStyle, weight: Int?, grade: Int?, opticalSize: Int?) {
        flow.value = IconStyleConfig(
            style = style,
            weight = weight ?: 400,
            grade = grade ?: 0,
            opticalSize = opticalSize ?: 24
        )
    }

    override fun supportedStyles(): List<IconStyle> = listOf(IconStyle.FILLED, IconStyle.OUTLINED, IconStyle.ROUNDED)
}