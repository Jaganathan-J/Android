package com.example.dynamicthemesync.data.source.local

import android.content.Context
import com.example.dynamicthemesync.data.mapper.ThemeTokensDto
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalThemeDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) {
    private val adapter = moshi.adapter(ThemeTokensDto::class.java)

    fun loadBundledTheme(): ThemeTokensDto? {
        return try {
            context.assets.open("themes/material3.json").bufferedReader().use { reader ->
                val json = reader.readText()
                adapter.fromJson(json)
            }
        } catch (e: Exception) {
            null
        }
    }
}