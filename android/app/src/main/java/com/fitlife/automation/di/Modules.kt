package com.fitlife.automation.di

import com.fitlife.automation.domain.repository.AuthRepository
import com.fitlife.automation.domain.repository.WorkflowRepository
import com.fitlife.automation.data.repository.MockAuthRepository
import com.fitlife.automation.data.repository.MockWorkflowRepository
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
    abstract fun bindAuthRepository(impl: MockAuthRepository): AuthRepository

    @Binds
    @Singleton
    abstract fun bindWorkflowRepository(impl: MockWorkflowRepository): WorkflowRepository
}

// Just a placeholder for network if needed
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // Retrofit setup would go here
}