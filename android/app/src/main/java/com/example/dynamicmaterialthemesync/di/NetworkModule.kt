package com.example.dynamicmaterialthemesync.di

import com.example.dynamicmaterialthemesync.data.source.remote.RemoteThemeApiService
import com.example.dynamicmaterialthemesync.data.source.remote.RemoteThemeDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DEFAULT_BASE_URL = "https://example.com/" // Used as placeholder

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(DEFAULT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideRemoteThemeApiService(retrofit: Retrofit): RemoteThemeApiService =
        retrofit.create(RemoteThemeApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteThemeDataSource(api: RemoteThemeApiService): RemoteThemeDataSource =
        RemoteThemeDataSource(api, DEFAULT_BASE_URL + "themes/material3.json")
}