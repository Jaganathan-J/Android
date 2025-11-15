package com.example.material3showcase.di

import com.example.material3showcase.data.repository.ConfigRepositoryImpl
import com.example.material3showcase.domain.repository.ConfigRepository
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
    abstract fun bindConfigRepository(impl: ConfigRepositoryImpl): ConfigRepository
}
