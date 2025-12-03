package com.example.automationoverflow.presentation.screens.wizard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automationoverflow.domain.model.Action
import com.example.automationoverflow.domain.model.Trigger
import com.example.automationoverflow.domain.repository.WorkflowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WizardViewModel @Inject constructor(
    private val repository: WorkflowRepository
) : ViewModel() {

    var workflowName by mutableStateOf("")
    
    var triggers by mutableStateOf<List<Trigger>>(emptyList())
    var selectedTrigger by mutableStateOf<Trigger?>(null)

    var actions by mutableStateOf<List<Action>>(emptyList())
    var selectedAction by mutableStateOf<Action?>(null)

    var isSaving by mutableStateOf(false)

    init {
        viewModelScope.launch {
            triggers = repository.getTriggers()
            actions = repository.getActions()
        }
    }

    fun saveAutomation(onSuccess: () -> Unit) {
        if (selectedTrigger == null || selectedAction == null) return
        
        isSaving = true
        viewModelScope.launch {
            repository.createWorkflow(workflowName, selectedTrigger!!, selectedAction!!)
            isSaving = false
            onSuccess()
        }
    }
}