package com.example.m3gallery.data.remote.api

import retrofit2.http.GET

interface ApiService {
    // Placeholder for future backend integration
    @GET("components")
    suspend fun getComponents(): List<Any>
}
