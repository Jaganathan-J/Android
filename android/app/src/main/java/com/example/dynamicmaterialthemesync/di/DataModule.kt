package com.example.dynamicmaterialthemesync.di

import android.content.Context
import com.example.dynamicmaterialthemesync.data.local.ThemePreferencesDataStore
import com.example.dynamicmaterialthemesync.data.repository.ThemeRepositoryImpl
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindThemeRepository(impl: ThemeRepositoryImpl): ThemeRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideThemePreferencesDataStore(
        @ApplicationContext context: Context
    ): ThemePreferencesDataStore = ThemePreferencesDataStore(context)
}