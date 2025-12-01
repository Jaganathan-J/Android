package com.wealthwise.app.di

import android.content.Context
import androidx.room.Room
import com.wealthwise.app.data.local.TransactionDao
import com.wealthwise.app.data.local.WealthWiseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WealthWiseDatabase {
        return Room.databaseBuilder(
            context,
            WealthWiseDatabase::class.java,
            "wealthwise.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: WealthWiseDatabase): TransactionDao = db.transactionDao()
}