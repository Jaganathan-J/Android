package com.example.fitlife.di

import com.example.fitlife.data.repository.MockWorkoutRepository
import com.example.fitlife.domain.repository.WorkoutRepository
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
    fun provideWorkoutRepository(): WorkoutRepository {
        return MockWorkoutRepository()
    }
}