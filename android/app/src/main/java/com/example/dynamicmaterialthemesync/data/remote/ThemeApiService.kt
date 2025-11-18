package com.example.dynamicmaterialthemesync.data.remote

import com.example.dynamicmaterialthemesync.domain.model.ThemeData
import retrofit2.http.GET

interface ThemeApiService {
    @GET("theme.json")
    suspend fun getTheme(): ThemeData
}