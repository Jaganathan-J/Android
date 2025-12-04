package com.example.fitlifefinance.di

import com.example.fitlifefinance.data.repository.MockRepositoryImpl
import com.example.fitlifefinance.domain.repository.AccountRepository
import com.example.fitlifefinance.domain.repository.BudgetRepository
import com.example.fitlifefinance.domain.repository.TransactionRepository
import com.example.fitlifefinance.domain.repository.UserRepository
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
    fun provideMockRepository(): MockRepositoryImpl {
        return MockRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(impl: MockRepositoryImpl): TransactionRepository = impl

    @Provides
    @Singleton
    fun provideAccountRepository(impl: MockRepositoryImpl): AccountRepository = impl

    @Provides
    @Singleton
    fun provideBudgetRepository(impl: MockRepositoryImpl): BudgetRepository = impl

    @Provides
    @Singleton
    fun provideUserRepository(impl: MockRepositoryImpl): UserRepository = impl
}