package com.example.dynamicmaterialthemesync.data.repository

import com.example.dynamicmaterialthemesync.domain.model.FontConfig
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository

class FontRepositoryImpl : FontRepository {

    // Simplified in-memory storage; in real app, persist fonts metadata
    private var config: FontConfig? = null

    override suspend fun getPreferredFont(): FontConfig? = config

    override suspend fun setPreferredFont(config: FontConfig) {
        this.config = config
    }
}