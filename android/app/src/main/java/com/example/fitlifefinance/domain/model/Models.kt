package com.example.fitlifefinance.domain.model

import java.util.Date

data class User(
    val id: String,
    val name: String,
    val email: String,
    val isPremium: Boolean
)

enum class TransactionType {
    INCOME, EXPENSE, TRANSFER
}

data class Transaction(
    val id: String,
    val title: String,
    val subtitle: String,
    val amount: Double,
    val date: Date,
    val type: TransactionType,
    val categoryName: String,
    // Simple enum for icon mapping
    val iconType: IconType = IconType.DEFAULT
)

enum class IconType {
    DEFAULT, SHOPPING, FOOD, BILLS, SALARY, COFFEE
}

data class Account(
    val id: String,
    val bankName: String,
    val accountType: String,
    val mask: String,
    val balance: Double,
    val status: AccountStatus
)

enum class AccountStatus {
    ACTIVE, DUE_SOON
}

data class BudgetCategory(
    val id: String,
    val name: String,
    val spent: Double,
    val total: Double,
    val colorHex: Long
) {
    val percent: Float get() = (spent / total).toFloat()
    val isOverBudget: Boolean get() = spent > total
}

data class WeeklyStats(
    val totalSpent: Double,
    val totalIncome: Double,
    val spendingByDay: List<Double>, // 7 days
    val categoryBreakdown: List<CategoryShare>
)

data class CategoryShare(
    val name: String,
    val percent: Float,
    val colorHex: Long
)