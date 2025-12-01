package com.wealthwise.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val merchant: String,
    val amount: Double,
    val currency: String,
    val categoryIcon: String,
    val timestamp: Long
)