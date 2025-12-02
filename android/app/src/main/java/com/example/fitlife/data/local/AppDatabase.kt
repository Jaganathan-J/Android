package com.example.fitlife.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitlife.data.local.entity.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}