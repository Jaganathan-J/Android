package com.example.financemanager.ui.screens.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanager.data.repository.MockRepository
import com.example.financemanager.domain.model.IntegrationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkflowBuilderViewModel @Inject constructor(
    private val repository: MockRepository
) : ViewModel() {

    private val _workflowName = MutableStateFlow("")
    val workflowName = _workflowName.asStateFlow()

    private val _selectedTrigger = MutableStateFlow<IntegrationItem?>(null)
    val selectedTrigger = _selectedTrigger.asStateFlow()

    private val _selectedAction = MutableStateFlow<IntegrationItem?>(null)
    val selectedAction = _selectedAction.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    private val _availableTriggers = MutableStateFlow<List<IntegrationItem>>(emptyList())
    val availableTriggers = _availableTriggers.asStateFlow()

    private val _availableActions = MutableStateFlow<List<IntegrationItem>>(emptyList())
    val availableActions = _availableActions.asStateFlow()

    init {
        viewModelScope.launch {
            _availableTriggers.value = repository.getTriggers()
            _availableActions.value = repository.getActions()
        }
    }

    fun setName(name: String) {
        _workflowName.value = name
    }

    fun selectTrigger(item: IntegrationItem) {
        _selectedTrigger.value = item
    }

    fun selectAction(item: IntegrationItem) {
        _selectedAction.value = item
    }

    fun saveWorkflow(onSuccess: () -> Unit) {
        val trigger = _selectedTrigger.value ?: return
        val action = _selectedAction.value ?: return
        val name = _workflowName.value

        viewModelScope.launch {
            _isSaving.value = true
            repository.saveWorkflow(name, trigger, action)
            _isSaving.value = false
            onSuccess()
        }
    }
}