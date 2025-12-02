package com.fitlife.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Provide Repositories, Database, Retrofit instances here
    // Keeping empty for UI Focus as per instructions, but setup for Clean Architecture
}