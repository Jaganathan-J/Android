package com.example.m3gallery.di

import com.example.m3gallery.data.repository.M3ComponentsRepositoryImpl
import com.example.m3gallery.domain.repository.M3ComponentsRepository
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
    abstract fun bindM3ComponentsRepository(
        impl: M3ComponentsRepositoryImpl
    ): M3ComponentsRepository
}
