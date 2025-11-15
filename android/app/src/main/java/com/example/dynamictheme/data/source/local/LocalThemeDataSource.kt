package com.example.dynamictheme.data.source.local

import android.content.Context
import com.example.dynamictheme.data.mapper.toDomain
import com.example.dynamictheme.data.remote.dto.ThemeTokensDto
import com.example.dynamictheme.domain.model.ThemeTokens
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class LocalThemeDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) {
    private val cacheFile: File
        get() = File(context.filesDir, "theme.json")

    suspend fun loadBundledTheme(assetName: String = "material3.json"): ThemeTokens? = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            context.assets.open(assetName).use { inputStream ->
                val json = inputStream.bufferedReader().readText()
                val adapter = moshi.adapter(ThemeTokensDto::class.java)
                adapter.fromJson(json)?.toDomain()
            }
        }.getOrNull()
    }

    suspend fun loadCachedTheme(): ThemeTokens? = withContext(Dispatchers.IO) {
        if (!cacheFile.exists()) return@withContext null
        return@withContext runCatching {
            val json = cacheFile.readText()
            val adapter = moshi.adapter(ThemeTokensDto::class.java)
            adapter.fromJson(json)?.toDomain()
        }.getOrNull()
    }

    suspend fun cacheTheme(tokens: ThemeTokens) = withContext(Dispatchers.IO) {
        runCatching {
            val adapter = moshi.adapter(ThemeTokensDto::class.java)
            val dto = ThemeTokensDto(
                schemaVersion = tokens.schemaVersion,
                themeVersion = tokens.themeVersion,
                name = tokens.name,
                colors = null,
                typography = null,
                shapes = null,
                elevation = null,
                icons = null,
                meta = null
            )
            cacheFile.writeText(adapter.toJson(dto))
        }
    }
}