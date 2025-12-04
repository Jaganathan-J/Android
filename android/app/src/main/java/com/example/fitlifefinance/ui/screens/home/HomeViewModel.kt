package com.example.fitlifefinance.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.fitlifefinance.domain.repository.AccountRepository
import com.example.fitlifefinance.domain.repository.TransactionRepository
import com.example.fitlifefinance.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {
    val user = userRepository.getCurrentUser()
    val totalBalance = accountRepository.getTotalBalance()
    val recentTransactions = transactionRepository.getRecentTransactions()
}