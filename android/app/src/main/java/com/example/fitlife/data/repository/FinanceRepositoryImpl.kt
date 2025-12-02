package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.*
import com.example.fitlife.domain.repository.FinanceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FinanceRepositoryImpl @Inject constructor() : FinanceRepository {

    // Using in-memory state for this demo/wireframe generation
    private val _transactions = MutableStateFlow<List<Transaction>>(
        listOf(
            Transaction("1", "Starbucks", "Food", Money(BigDecimal("-5.50")), Instant.now()),
            Transaction("2", "Uber", "Transport", Money(BigDecimal("-12.40")), Instant.now()),
            Transaction("3", "Amazon", "Shopping", Money(BigDecimal("-45.99")), Instant.now())
        )
    )

    override fun getTotalBalance(): Flow<Money> = _transactions.map {
        Money(BigDecimal("5234.50")) // Hardcoded as per wireframe
    }

    override fun getMonthlySpending(): Flow<Money> = _transactions.map {
        Money(BigDecimal("1234.56")) // Hardcoded wireframe value
    }

    override fun getTransactions(): Flow<List<Transaction>> = _transactions.asStateFlow()

    override fun getBudget(): Flow<Budget> = _transactions.map {
        Budget(
            total = Money(BigDecimal("5000.00")),
            categories = listOf(
                BudgetCategory("Food", Money(BigDecimal("250.00")), Money(BigDecimal("500.00"))),
                BudgetCategory("Transport", Money(BigDecimal("100.00")), Money(BigDecimal("300.00"))),
                BudgetCategory("Entertainment", Money(BigDecimal("80.00")), Money(BigDecimal("100.00")))
            )
        )
    }

    override suspend fun addTransaction(transaction: Transaction) {
        val current = _transactions.value.toMutableList()
        current.add(0, transaction)
        _transactions.emit(current)
    }
}