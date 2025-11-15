package com.example.material3showcase.data.remote.api

import com.example.material3showcase.data.remote.dto.ButtonConfigDto
import retrofit2.http.GET

interface ConfigApi {
    @GET("/api/v1/buttons")
    suspend fun getButtonsConfig(): List<ButtonConfigDto>
}
