package com.luxemarket.app.di

import com.luxemarket.app.data.remote.LuxeApi
import com.luxemarket.app.data.repository.ProductRepositoryImpl
import com.luxemarket.app.domain.repository.ProductRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideLuxeApi(okHttpClient: OkHttpClient, moshi: Moshi): LuxeApi {
        // Mock Base URL for demo purposes, in Prod use BuildConfig.BASE_URL
        return Retrofit.Builder()
            .baseUrl("https://mock.api.luxemarket.com/") 
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(LuxeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: LuxeApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }
}