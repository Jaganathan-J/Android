package com.example.dynamicmaterialthemesync.data.remote

import com.example.dynamicmaterialthemesync.data.remote.dto.RemoteThemeTokensDto
import retrofit2.http.GET

interface ThemeApiService {

    // Endpoint expected to return the Figma Theme Builder JSON tokens
    @GET("theme/tokens.json")
    suspend fun getThemeTokens(): RemoteThemeTokensDto
}