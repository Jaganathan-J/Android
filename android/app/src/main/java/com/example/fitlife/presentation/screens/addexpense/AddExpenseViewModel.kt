package com.example.fitlife.presentation.screens.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.Money
import com.example.fitlife.domain.model.Transaction
import com.example.fitlife.domain.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {

    fun saveExpense(amountStr: String, category: String, description: String) {
        val amountVal = amountStr.toDoubleOrNull() ?: 0.0
        val amount = Money(BigDecimal(amountVal).negate()) // Expense is negative
        
        val transaction = Transaction(
            id = UUID.randomUUID().toString(),
            merchant = description.ifBlank { "Manual Entry" },
            category = category.ifBlank { "Uncategorized" },
            amount = amount,
            timestamp = Instant.now()
        )

        viewModelScope.launch {
            repository.addTransaction(transaction)
        }
    }
}