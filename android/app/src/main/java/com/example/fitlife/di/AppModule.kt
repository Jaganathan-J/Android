package com.example.fitlife.di

import com.example.fitlife.data.repository.MockFitnessRepository
import com.example.fitlife.domain.repository.FitnessRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindFitnessRepository(
        mock: MockFitnessRepository
    ): FitnessRepository
}