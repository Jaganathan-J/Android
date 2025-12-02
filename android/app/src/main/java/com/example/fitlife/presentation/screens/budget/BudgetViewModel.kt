package com.example.fitlife.presentation.screens.budget

import androidx.lifecycle.ViewModel
import com.example.fitlife.domain.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    repository: FinanceRepository
) : ViewModel() {
    val budget = repository.getBudget()
}