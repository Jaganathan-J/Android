package com.example.fitlife.di

import com.example.fitlife.data.WorkoutRepositoryImpl
import com.example.fitlife.domain.WorkoutRepository
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
        return WorkoutRepositoryImpl()
    }
}