package com.example.fitlifefinance.data.repository

import com.example.fitlifefinance.domain.model.*
import com.example.fitlifefinance.domain.repository.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor() : TransactionRepository, AccountRepository, BudgetRepository, UserRepository {

    private val _transactions = MutableStateFlow(generateMockTransactions())
    private val _accounts = MutableStateFlow(generateMockAccounts())
    private val _currentUser = MutableStateFlow(User("1", "John Doe", "john.doe@email.com", true))

    override fun getRecentTransactions(): Flow<List<Transaction>> {
        return _transactions.map { it.take(4) }
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return _transactions
    }

    override suspend fun addTransaction(transaction: Transaction) {
        val current = _transactions.value.toMutableList()
        current.add(0, transaction)
        _transactions.emit(current)
    }

    override fun getAccounts(): Flow<List<Account>> = _accounts

    override fun getTotalBalance(): Flow<Double> = _accounts.map { list ->
        list.sumOf { it.balance }
    }

    override fun getBudgetCategories(): Flow<List<BudgetCategory>> {
        return MutableStateFlow(listOf(
            BudgetCategory("1", "Housing", 1500.0, 1500.0, 0xFF007BFF),
            BudgetCategory("2", "Food & Dining", 350.0, 430.0, 0xFFFD7E14),
            BudgetCategory("3", "Entertainment", 380.0, 300.0, 0xFFE53935),
            BudgetCategory("4", "Transportation", 120.0, 200.0, 0xFF4CAF50)
        ))
    }

    override fun getCurrentUser(): Flow<User?> = _currentUser

    override fun getWeeklyStats(): Flow<WeeklyStats> = MutableStateFlow(
        WeeklyStats(
            totalSpent = 892.50,
            totalIncome = 4500.0,
            spendingByDay = listOf(50.0, 120.0, 80.0, 400.0, 60.0, 150.0, 32.5),
            categoryBreakdown = listOf(
                CategoryShare("Food", 0.35f, 0xFF4CAF50),
                CategoryShare("Bills", 0.25f, 0xFF007BFF),
                CategoryShare("Shopping", 0.20f, 0xFFFD7E14),
                CategoryShare("Entertainment", 0.12f, 0xFFE53935),
                CategoryShare("Other", 0.08f, 0xFF9E9E9E)
            )
        )
    )

    private fun generateMockTransactions(): List<Transaction> {
        val cal = Calendar.getInstance()
        return listOf(
            Transaction("1", "Grocery Shopping", "Whole Foods • 2:30 PM", -85.50, cal.time, TransactionType.EXPENSE, "Food", IconType.FOOD),
            Transaction("2", "Starbucks", "Coffee • 8:15 AM", -6.75, cal.time, TransactionType.EXPENSE, "Dining", IconType.COFFEE),
            Transaction("3", "Salary Received", "Direct Deposit", 4500.00, cal.time, TransactionType.INCOME, "Income", IconType.SALARY),
            Transaction("4", "Electric Bill", "Utility • Yesterday", -150.00, Date(System.currentTimeMillis() - 86400000), TransactionType.EXPENSE, "Bills", IconType.BILLS),
            Transaction("5", "Nike Store", "Shopping • Dec 1", -120.00, Date(System.currentTimeMillis() - 172800000), TransactionType.EXPENSE, "Shopping", IconType.SHOPPING)
        )
    }

    private fun generateMockAccounts(): List<Account> {
        return listOf(
            Account("1", "Chase", "Checking", "4521", 12450.00, AccountStatus.ACTIVE),
            Account("2", "Bank of America", "Savings", "8834", 28500.00, AccountStatus.ACTIVE),
            Account("3", "Wells Fargo", "Credit Card", "2291", -2340.00, AccountStatus.DUE_SOON),
            Account("4", "Ally", "High Yield Savings", "7756", 10152.50, AccountStatus.ACTIVE)
        )
    }
}