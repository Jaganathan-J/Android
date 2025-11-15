package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.data.mapper.ThemeTokensDto
import com.example.dynamicmaterialthemesync.data.mapper.toDomain
import com.example.dynamicmaterialthemesync.data.source.local.LocalThemeDataSource
import com.example.dynamicmaterialthemesync.data.source.preferences.ThemePreferencesDataSource
import com.example.dynamicmaterialthemesync.data.source.remote.RemoteThemeDataSource
import com.example.dynamicmaterialthemesync.domain.model.SyncInfo
import com.example.dynamicmaterialthemesync.domain.model.ThemeSource
import com.example.dynamicmaterialthemesync.domain.model.ThemeTokens
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ThemeRepositoryImpl(
    private val remoteDataSource: RemoteThemeDataSource,
    private val localDataSource: LocalThemeDataSource,
    private val prefsDataSource: ThemePreferencesDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ThemeRepository {

    private val tokensState = MutableStateFlow<ThemeTokens?>(null)

    override fun getThemeTokens(): Flow<ThemeTokens?> = tokensState.asStateFlow()

    override suspend fun syncThemeTokens(source: ThemeSource): Result<ThemeTokens> = withContext(ioDispatcher) {
        runCatching {
            val dto: ThemeTokensDto = when (source.type) {
                com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType.SYSTEM_DYNAMIC -> {
                    // fall back to remote
                    remoteDataSource.fetchTheme(source.remoteUrl)
                }
                com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType.REMOTE -> {
                    remoteDataSource.fetchTheme(source.remoteUrl)
                }
                com.example.dynamicmaterialthemesync.domain.model.ThemeSourceType.LOCAL -> {
                    val asset = source.localAssetName ?: "material3_theme.json"
                    localDataSource.loadBundled(asset) ?: throw IllegalStateException("Local theme missing")
                }
            }
            val domain = dto.toDomain()
            cacheThemeTokens(domain)
            domain
        }
    }

    override suspend fun getLocalThemeTokens(): ThemeTokens? = withContext(ioDispatcher) {
        localDataSource.loadCached()?.toDomain()
    }

    override suspend fun cacheThemeTokens(tokens: ThemeTokens) = withContext(ioDispatcher) {
        tokensState.value = tokens
        // Also cache to disk
        val dto = ThemeTokensDto(
            schemaVersion = tokens.schemaVersion,
            themeVersion = tokens.themeVersion,
            name = tokens.name,
            colors = com.example.dynamicmaterialthemesync.data.mapper.ColorsDto(
                light = com.example.dynamicmaterialthemesync.data.mapper.ColorSchemeDto(
                    primary = tokens.colors.light.primary,
                    onPrimary = tokens.colors.light.onPrimary,
                    primaryContainer = tokens.colors.light.primaryContainer,
                    onPrimaryContainer = tokens.colors.light.onPrimaryContainer,
                    secondary = tokens.colors.light.secondary,
                    onSecondary = tokens.colors.light.onSecondary,
                    background = tokens.colors.light.background,
                    onBackground = tokens.colors.light.onBackground,
                    surface = tokens.colors.light.surface,
                    onSurface = tokens.colors.light.onSurface,
                    surfaceVariant = tokens.colors.light.surfaceVariant,
                    onSurfaceVariant = tokens.colors.light.onSurfaceVariant,
                    tertiary = tokens.colors.light.tertiary,
                    error = tokens.colors.light.error,
                    onError = tokens.colors.light.onError,
                    outline = tokens.colors.light.outline,
                    inverseSurface = tokens.colors.light.inverseSurface,
                    inverseOnSurface = tokens.colors.light.inverseOnSurface
                ),
                dark = com.example.dynamicmaterialthemesync.data.mapper.ColorSchemeDto(
                    primary = tokens.colors.dark.primary,
                    onPrimary = tokens.colors.dark.onPrimary,
                    primaryContainer = tokens.colors.dark.primaryContainer,
                    onPrimaryContainer = tokens.colors.dark.onPrimaryContainer,
                    secondary = tokens.colors.dark.secondary,
                    onSecondary = tokens.colors.dark.onSecondary,
                    background = tokens.colors.dark.background,
                    onBackground = tokens.colors.dark.onBackground,
                    surface = tokens.colors.dark.surface,
                    onSurface = tokens.colors.dark.onSurface,
                    surfaceVariant = tokens.colors.dark.surfaceVariant,
                    onSurfaceVariant = tokens.colors.dark.onSurfaceVariant,
                    tertiary = tokens.colors.dark.tertiary,
                    error = tokens.colors.dark.error,
                    onError = tokens.colors.dark.onError,
                    outline = tokens.colors.dark.outline,
                    inverseSurface = tokens.colors.dark.inverseSurface,
                    inverseOnSurface = tokens.colors.dark.inverseOnSurface
                )
            ),
            typography = tokens.typography?.let {
                com.example.dynamicmaterialthemesync.data.mapper.TypographyDto(
                    fontFamily = it.fontFamily,
                    fallback = it.fallback,
                    weightMapping = it.weightMapping,
                    sizeSp = it.sizeSp,
                    lineHeightSp = it.lineHeightSp,
                    letterSpacingEm = it.letterSpacingEm
                )
            },
            shapes = tokens.shapes?.let {
                com.example.dynamicmaterialthemesync.data.mapper.ShapesDto(
                    cornerFamily = it.cornerFamily,
                    extraSmall = it.extraSmall,
                    small = it.small,
                    medium = it.medium,
                    large = it.large,
                    extraLarge = it.extraLarge
                )
            },
            elevation = tokens.elevation?.let {
                com.example.dynamicmaterialthemesync.data.mapper.ElevationDto(
                    level0 = it.level0,
                    level1 = it.level1,
                    level2 = it.level2,
                    level3 = it.level3,
                    level4 = it.level4,
                    level5 = it.level5
                )
            },
            icons = tokens.icons?.let {
                com.example.dynamicmaterialthemesync.data.mapper.IconsDto(
                    materialSymbolsStyle = it.materialSymbolsStyle,
                    defaultWeight = it.defaultWeight,
                    defaultGrade = it.defaultGrade,
                    defaultOpticalSize = it.defaultOpticalSize
                )
            },
            meta = tokens.meta?.let {
                com.example.dynamicmaterialthemesync.data.mapper.MetaDto(
                    generatedBy = it.generatedBy,
                    updatedAt = it.updatedAt
                )
            }
        )
        localDataSource.saveCache(dto)
    }

    override fun getLastSyncInfo(): Flow<SyncInfo> {
        return prefsDataSource.observeThemePreferences().map { prefs ->
            SyncInfo(
                lastSyncTimeMillis = prefs.lastSyncTimeMillis,
                themeVersion = prefs.lastThemeVersion
            )
        }
    }
}