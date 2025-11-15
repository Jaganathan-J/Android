package com.example.dynamicthemesync.data.source.remote

import com.example.dynamicthemesync.data.mapper.ThemeTokensDto
import retrofit2.http.GET
import retrofit2.http.Url

interface RemoteThemeService {
    @GET
    suspend fun fetchThemeJson(@Url url: String): ThemeTokensDto
}