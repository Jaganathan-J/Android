package com.example.fitlife.domain.model

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

data class Money(
    val amount: BigDecimal,
    val currencyCode: String = "USD"
) {
    val formatted: String
        get() = NumberFormat.getCurrencyInstance(Locale.US).format(amount)
}