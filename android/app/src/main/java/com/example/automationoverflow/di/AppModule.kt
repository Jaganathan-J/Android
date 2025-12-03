package com.example.automationoverflow.di

import com.example.automationoverflow.data.repository.FakeWorkflowRepository
import com.example.automationoverflow.domain.repository.WorkflowRepository
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
        return FakeWorkflowRepository()
    }
}