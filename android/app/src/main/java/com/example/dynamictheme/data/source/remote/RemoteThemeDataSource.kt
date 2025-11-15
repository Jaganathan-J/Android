package com.example.dynamictheme.data.source.remote

import com.example.dynamictheme.data.mapper.toDomain
import com.example.dynamictheme.data.remote.api.ThemeApiService
import com.example.dynamictheme.domain.model.ThemeTokens
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

class RemoteThemeDataSource @Inject constructor(
    private val apiService: ThemeApiService,
    private val moshi: Moshi
) {
    suspend fun fetchTheme(url: String): Result<ThemeTokens> = withContext(Dispatchers.IO) {
        return@withContext runCatching {
            val response = apiService.fetchThemeJson(url)
            if (!response.isSuccessful) {
                throw IllegalStateException("HTTP ${'$'}{response.code()}")
            }
            val body: ResponseBody = requireNotNull(response.body())
            val json = body.string()
            val adapter = moshi.adapter(com.example.dynamictheme.data.remote.dto.ThemeTokensDto::class.java)
            val dto = requireNotNull(adapter.fromJson(json))
            dto.toDomain()
        }
    }
}