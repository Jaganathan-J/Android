package com.example.dynamicmaterialthemesync.data.source.local

import android.content.Context
import com.example.dynamicmaterialthemesync.data.mapper.ThemeTokensDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.File

class LocalThemeDataSource(
    private val context: Context,
    private val moshi: Moshi,
    private val cacheFileName: String = "theme_cache.json"
) {
    private val adapter: JsonAdapter<ThemeTokensDto> = moshi.adapter(ThemeTokensDto::class.java)

    fun loadBundled(assetName: String): ThemeTokensDto? {
        return try {
            val json = context.assets.open(assetName).bufferedReader().use { it.readText() }
            adapter.fromJson(json)
        } catch (e: Exception) {
            null
        }
    }

    fun loadCached(): ThemeTokensDto? {
        val file = File(context.filesDir, cacheFileName)
        if (!file.exists()) return null
        return try {
            val json = file.readText()
            adapter.fromJson(json)
        } catch (e: Exception) {
            null
        }
    }

    fun saveCache(dto: ThemeTokensDto) {
        runCatching {
            val json = adapter.toJson(dto)
            val file = File(context.filesDir, cacheFileName)
            file.writeText(json)
        }
    }
}