package com.example.dynamicmaterialthemesync.di

import com.example.dynamicmaterialthemesync.core.platform.DynamicColorProviderImpl
import com.example.dynamicmaterialthemesync.data.repository.FontRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.IconRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.PreferencesRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.ThemeRepositoryImpl
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindThemeRepository(impl: ThemeRepositoryImpl): ThemeRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    @Singleton
    abstract fun bindFontRepository(impl: FontRepositoryImpl): FontRepository

    @Binds
    @Singleton
    abstract fun bindIconRepository(impl: IconRepositoryImpl): IconRepository

    @Binds
    @Singleton
    abstract fun bindDynamicColorProvider(impl: DynamicColorProviderImpl): DynamicColorProvider
}