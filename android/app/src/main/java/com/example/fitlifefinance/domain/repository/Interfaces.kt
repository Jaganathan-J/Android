package com.example.fitlifefinance.domain.repository

import com.example.fitlifefinance.domain.model.*
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getRecentTransactions(): Flow<List<Transaction>>
    fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
}

interface AccountRepository {
    fun getAccounts(): Flow<List<Account>>
    fun getTotalBalance(): Flow<Double>
}

interface BudgetRepository {
    fun getBudgetCategories(): Flow<List<BudgetCategory>>
}

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    fun getWeeklyStats(): Flow<WeeklyStats>
}