package com.example.dynamicmaterialthemesync.data.source.remote

import com.example.dynamicmaterialthemesync.data.mapper.ThemeTokensDto
import retrofit2.http.GET
import retrofit2.http.Url

interface RemoteThemeApiService {
    @GET
    suspend fun fetchThemeJson(@Url url: String): ThemeTokensDto
}

class RemoteThemeDataSource(
    private val api: RemoteThemeApiService,
    private val defaultUrl: String
) {
    suspend fun fetchTheme(url: String?): ThemeTokensDto {
        return api.fetchThemeJson(url ?: defaultUrl)
    }
}