package com.example.dynamicmaterialthemesync.data.source.local

import android.content.Context
import com.example.dynamicmaterialthemesync.data.source.remote.ThemeJsonResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(com.squareup.moshi.ExperimentalMoshiApi::class)
@Singleton
class LocalThemeDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) {
    private val adapter = moshi.adapter<ThemeJsonResponse>()

    fun loadBundledTheme(): ThemeJsonResponse? {
        return try {
            context.assets.open("themes/material3.json").use { input ->
                val json = input.bufferedReader().readText()
                adapter.fromJson(json)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun loadCachedTheme(): ThemeJsonResponse? {
        return try {
            val file = File(context.filesDir, "cached_theme.json")
            if (!file.exists()) return null
            val json = file.readText()
            adapter.fromJson(json)
        } catch (e: Exception) {
            null
        }
    }

    fun cacheTheme(response: ThemeJsonResponse) {
        try {
            val file = File(context.filesDir, "cached_theme.json")
            val json = adapter.toJson(response)
            file.writeText(json)
        } catch (_: Exception) {
        }
    }
}