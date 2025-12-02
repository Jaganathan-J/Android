package com.example.fitlife.domain.model

import java.time.Instant

data class Transaction(
    val id: String,
    val merchant: String,
    val category: String,
    val amount: Money,
    val timestamp: Instant
)