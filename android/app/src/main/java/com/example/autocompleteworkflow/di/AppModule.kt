package com.example.autocompleteworkflow.di

import com.example.autocompleteworkflow.data.repository.MockWorkflowRepositoryImpl
import com.example.autocompleteworkflow.domain.repository.WorkflowRepository
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
    fun provideWorkflowRepository(): WorkflowRepository {
        return MockWorkflowRepositoryImpl()
    }
}