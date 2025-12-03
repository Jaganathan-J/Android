package com.automationoverflow.app.di

import com.automationoverflow.app.data.repository.MockWorkflowRepository
import com.automationoverflow.app.domain.repository.WorkflowRepository
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
        return MockWorkflowRepository()
    }
}