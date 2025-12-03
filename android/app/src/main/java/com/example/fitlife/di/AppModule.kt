package com.example.fitlife.di

import com.example.fitlife.data.repository.AuthRepositoryImpl
import com.example.fitlife.data.repository.AutomationRepositoryImpl
import com.example.fitlife.domain.repository.AuthRepository
import com.example.fitlife.domain.repository.AutomationRepository
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
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl()

    @Provides
    @Singleton
    fun provideAutomationRepository(): AutomationRepository = AutomationRepositoryImpl()
}