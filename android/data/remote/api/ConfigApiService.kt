package com.example.material3showcase.data.remote.api

import com.example.material3showcase.data.remote.dto.ButtonDto
import retrofit2.http.GET

interface ConfigApiService {
    @GET("/api/v1/buttons")
    suspend fun getButtons(): List<ButtonDto>
}
