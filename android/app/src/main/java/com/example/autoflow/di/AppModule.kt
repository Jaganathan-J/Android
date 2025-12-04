package com.example.autoflow.di

import com.example.autoflow.data.repository.MockAuthRepository
import com.example.autoflow.data.repository.MockWorkflowRepository
import com.example.autoflow.domain.repository.AuthRepository
import com.example.autoflow.domain.repository.WorkflowRepository
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
    fun provideAuthRepository(): AuthRepository {
        return MockAuthRepository()
    }

    @Provides
    @Singleton
    fun provideWorkflowRepository(): WorkflowRepository {
        return MockWorkflowRepository()
    }
}