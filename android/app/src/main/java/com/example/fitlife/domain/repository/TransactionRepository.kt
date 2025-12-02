package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    fun getTotalBalance(): Flow<Double>
    fun getSpendingReport(): Flow<Map<Long, Double>> // Date -> Amount
}