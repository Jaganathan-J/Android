package com.example.material3showcase.domain.repository

import com.example.material3showcase.domain.model.ButtonConfig

interface ConfigRepository {
    suspend fun fetchButtonConfigs(): Result<List<ButtonConfig>>
}
