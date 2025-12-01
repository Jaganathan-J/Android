package com.wealthwise.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wealthwise.app.data.local.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class WealthWiseDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}