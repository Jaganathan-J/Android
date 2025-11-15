package com.example.m3gallery.di

import com.example.m3gallery.data.repository.M3ComponentsRepositoryImpl
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import com.example.m3gallery.domain.usecase.GetComponentListUseCase
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideM3ComponentsRepository(): M3ComponentsRepository = M3ComponentsRepositoryImpl()

    @Provides
    @Singleton
    fun provideGetComponentListUseCase(
        repository: M3ComponentsRepository
    ): GetComponentListUseCase = GetComponentListUseCase(repository)

    @Provides
    @Singleton
    fun provideTriggerSnackbarUseCase(): TriggerSnackbarUseCase = TriggerSnackbarUseCase()
}
