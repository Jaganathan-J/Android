package com.example.fitlife.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlife.domain.model.Transaction
import com.example.fitlife.domain.model.TransactionType
import com.example.fitlife.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    fun addTransaction(amount: String, category: String, notes: String, type: TransactionType) {
        viewModelScope.launch {
            val amountDouble = amount.toDoubleOrNull() ?: 0.0
            val transaction = Transaction(
                amount = amountDouble,
                categoryName = category,
                notes = notes,
                type = type,
                date = System.currentTimeMillis()
            )
            repository.addTransaction(transaction)
        }
    }
}