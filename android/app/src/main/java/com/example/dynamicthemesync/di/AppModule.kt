package com.example.dynamicthemesync.di

import android.content.Context
import com.example.dynamicthemesync.core.platform.DynamicColorProviderImpl
import com.example.dynamicthemesync.data.repository.FontRepositoryImpl
import com.example.dynamicthemesync.data.repository.IconRepositoryImpl
import com.example.dynamicthemesync.data.repository.PreferencesRepositoryImpl
import com.example.dynamicthemesync.data.repository.ThemeRepositoryImpl
import com.example.dynamicthemesync.data.source.remote.RemoteThemeService
import com.example.dynamicthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicthemesync.domain.repository.FontRepository
import com.example.dynamicthemesync.domain.repository.IconRepository
import com.example.dynamicthemesync.domain.repository.PreferencesRepository
import com.example.dynamicthemesync.domain.repository.ThemeRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindThemeRepository(impl: ThemeRepositoryImpl): ThemeRepository

    @Binds
    abstract fun bindFontRepository(impl: FontRepositoryImpl): FontRepository

    @Binds
    abstract fun bindIconRepository(impl: IconRepositoryImpl): IconRepository

    @Binds
    abstract fun bindPreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    abstract fun bindDynamicColorProvider(impl: DynamicColorProviderImpl): DynamicColorProvider
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://example.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteThemeService(retrofit: Retrofit): RemoteThemeService =
        retrofit.create(RemoteThemeService::class.java)
}