package com.example.dynamictheme.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ThemeApiService {
    @GET
    suspend fun fetchThemeJson(@Url url: String): Response<ResponseBody>
}