package com.example.dynamicmaterialthemesync.di

import android.content.Context
import com.example.dynamicmaterialthemesync.core.platform.DynamicColorProviderImpl
import com.example.dynamicmaterialthemesync.data.repository.FontRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.IconRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.PreferencesRepositoryImpl
import com.example.dynamicmaterialthemesync.data.repository.ThemeRepositoryImpl
import com.example.dynamicmaterialthemesync.data.source.local.LocalThemeDataSource
import com.example.dynamicmaterialthemesync.data.source.preferences.ThemePreferencesDataSource
import com.example.dynamicmaterialthemesync.data.source.remote.RemoteThemeDataSource
import com.example.dynamicmaterialthemesync.domain.repository.DynamicColorProvider
import com.example.dynamicmaterialthemesync.domain.repository.FontRepository
import com.example.dynamicmaterialthemesync.domain.repository.IconRepository
import com.example.dynamicmaterialthemesync.domain.repository.PreferencesRepository
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideLocalThemeDataSource(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): LocalThemeDataSource = LocalThemeDataSource(context, moshi)

    @Provides
    @Singleton
    fun provideThemePreferencesDataSource(
        @ApplicationContext context: Context
    ): ThemePreferencesDataSource = ThemePreferencesDataSource(context)

    @Provides
    @Singleton
    fun provideThemeRepository(
        remote: RemoteThemeDataSource,
        local: LocalThemeDataSource,
        prefs: ThemePreferencesDataSource,
        ioDispatcher: CoroutineDispatcher
    ): ThemeRepository = ThemeRepositoryImpl(remote, local, prefs, ioDispatcher)

    @Provides
    @Singleton
    fun providePreferencesRepository(
        dataSource: ThemePreferencesDataSource
    ): PreferencesRepository = PreferencesRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideFontRepository(): FontRepository = FontRepositoryImpl()

    @Provides
    @Singleton
    fun provideIconRepository(): IconRepository = IconRepositoryImpl()

    @Provides
    @Singleton
    fun provideDynamicColorProvider(@ApplicationContext context: Context): DynamicColorProvider =
        DynamicColorProviderImpl(context)
}