package com.example.dynamicmaterialthemesync.data.source.local

import android.content.Context
import com.example.dynamicmaterialthemesync.data.mapper.ThemeDtoMapper
import com.example.dynamicmaterialthemesync.data.source.remote.ThemeJsonDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalThemeDataSource(
    private val context: Context
) {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    suspend fun loadFromAsset(assetName: String): ThemeJsonDto? = withContext(Dispatchers.IO) {
        return@withContext try {
            val json = context.assets.open(assetName).bufferedReader().use { it.readText() }
            val adapter = moshi.adapter(ThemeJsonDto::class.java)
            adapter.fromJson(json)
        } catch (_: Exception) {
            null
        }
    }
}