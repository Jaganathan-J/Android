package com.example.fitlife.domain.repository

import com.example.fitlife.domain.model.Budget
import com.example.fitlife.domain.model.Money
import com.example.fitlife.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {
    fun getTotalBalance(): Flow<Money>
    fun getMonthlySpending(): Flow<Money>
    fun getTransactions(): Flow<List<Transaction>>
    fun getBudget(): Flow<Budget>
    suspend fun addTransaction(transaction: Transaction)
}