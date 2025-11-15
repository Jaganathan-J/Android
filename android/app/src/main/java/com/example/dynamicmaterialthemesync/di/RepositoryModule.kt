package com.example.dynamicmaterialthemesync.di

import com.example.dynamicmaterialthemesync.data.repository.ThemeRepositoryImpl
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
    abstract fun bindThemeRepository(
        impl: ThemeRepositoryImpl
    ): ThemeRepository
}