package com.luxemarket.app.data.remote

import com.luxemarket.app.data.remote.dto.FeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LuxeApi {
    @GET("v1/feed")
    suspend fun getFeed(
        @Query("page") page: Int = 1,
        @Query("sort") sort: String = "newest"
    ): FeedResponse
}