package com.example.fitlife.presentation.screens.transactions

import androidx.lifecycle.ViewModel
import com.example.fitlife.domain.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    repository: FinanceRepository
) : ViewModel() {
    val transactions = repository.getTransactions()
}