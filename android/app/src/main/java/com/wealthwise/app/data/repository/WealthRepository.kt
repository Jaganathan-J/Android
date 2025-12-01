package com.wealthwise.app.data.repository

import com.wealthwise.app.data.local.TransactionDao
import com.wealthwise.app.data.local.TransactionEntity
import com.wealthwise.app.domain.model.DashboardData
import com.wealthwise.app.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WealthRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    // Simulate network delay and data for MVP
    private suspend fun seedDataIfNeeded() {
        val mockList = listOf(
            TransactionEntity("1", "Starbucks", -8.50, "USD", "coffee", System.currentTimeMillis()),
            TransactionEntity("2", "Uber Returns", -24.00, "USD", "transport", System.currentTimeMillis() - 86400000),
            TransactionEntity("3", "Salary", 3500.00, "USD", "income", System.currentTimeMillis() - 172800000),
            TransactionEntity("4", "Amazon", -120.45, "USD", "shopping", System.currentTimeMillis() - 200000000)
        )
        // In a real app check if empty
        transactionDao.insertAll(mockList)
    }

    fun getDashboardData(): Flow<DashboardData> {
        return transactionDao.getAllTransactions().map { entities ->
            if (entities.isEmpty()) {
               seedDataIfNeeded()
            }
            val mappedTransactions = entities.map { 
                Transaction(it.id, it.merchant, it.amount, it.currency, it.categoryIcon, it.timestamp) 
            }
            
            DashboardData(
                netWorth = 125000.50,
                currency = "USD",
                changePct = 2.4,
                spendingTotal = 3200.00,
                spendingLimit = 4000.00,
                recentTransactions = mappedTransactions
            )
        }
    }
}