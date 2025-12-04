package com.example.financemanager.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import com.example.financemanager.data.repository.MockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    repository: MockRepository
) : ViewModel() {
    val history = repository.history
}