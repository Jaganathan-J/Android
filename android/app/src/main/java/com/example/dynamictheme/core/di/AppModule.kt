package com.example.dynamictheme.core.di

import com.example.dynamictheme.core.platform.DynamicColorProviderImpl
import com.example.dynamictheme.data.repository.FontRepositoryImpl
import com.example.dynamictheme.data.repository.IconRepositoryImpl
import com.example.dynamictheme.data.repository.PreferencesRepositoryImpl
import com.example.dynamictheme.data.repository.ThemeRepositoryImpl
import com.example.dynamictheme.domain.repository.DynamicColorProvider
import com.example.dynamictheme.domain.repository.FontRepository
import com.example.dynamictheme.domain.repository.IconRepository
import com.example.dynamictheme.domain.repository.PreferencesRepository
import com.example.dynamictheme.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinds {

    @Binds
    @Singleton
    abstract fun bindThemeRepository(impl: ThemeRepositoryImpl): ThemeRepository

    @Binds
    @Singleton
    abstract fun bindFontRepository(impl: FontRepositoryImpl): FontRepository

    @Binds
    @Singleton
    abstract fun bindIconRepository(impl: IconRepositoryImpl): IconRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    @Singleton
    abstract fun bindDynamicColorProvider(impl: DynamicColorProviderImpl): DynamicColorProvider
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}