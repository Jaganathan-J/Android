package com.wealthwise.app.domain.model

data class DashboardData(
    val netWorth: Double,
    val currency: String,
    val changePct: Double,
    val spendingTotal: Double,
    val spendingLimit: Double,
    val recentTransactions: List<Transaction>
)

data class Transaction(
    val id: String,
    val merchant: String,
    val amount: Double,
    val currency: String,
    val categoryIcon: String,
    val timestamp: Long
)