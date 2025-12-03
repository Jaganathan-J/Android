package com.automationoverflow.app.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.automationoverflow.app.domain.models.WorkflowItem
import com.automationoverflow.app.domain.repository.WorkflowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    repository: WorkflowRepository
) : ViewModel() {
    val history: StateFlow<List<WorkflowItem>> = repository.getExecutionHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}