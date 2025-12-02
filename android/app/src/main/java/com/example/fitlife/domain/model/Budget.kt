package com.example.fitlife.domain.model

data class Budget(
    val total: Money,
    val categories: List<BudgetCategory>
)

data class BudgetCategory(
    val name: String,
    val spent: Money,
    val limit: Money
) {
    val progress: Float
        get() = if (limit.amount.toFloat() == 0f) 0f else spent.amount.toFloat() / limit.amount.toFloat()
}