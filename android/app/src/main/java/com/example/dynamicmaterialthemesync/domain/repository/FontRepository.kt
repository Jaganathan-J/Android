package com.example.dynamicmaterialthemesync.domain.repository

import com.example.dynamicmaterialthemesync.domain.model.FontConfig

interface FontRepository {
    suspend fun getPreferredFont(): FontConfig?
    suspend fun setPreferredFont(config: FontConfig)
}