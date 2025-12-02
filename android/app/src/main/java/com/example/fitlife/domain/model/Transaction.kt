package com.example.fitlife.domain.model

enum class TransactionType(val label: String) {
    EXPENSE("Expense"),
    INCOME("Income")
}

data class Transaction(
    val id: Long = 0,
    val amount: Double,
    val type: TransactionType,
    val categoryName: String,
    val date: Long, // Epoch Millis
    val notes: String? = null
)