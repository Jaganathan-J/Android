package com.automationoverflow.app.presentation.wizard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.automationoverflow.app.domain.models.ActionOption
import com.automationoverflow.app.domain.models.TriggerOption
import com.automationoverflow.app.domain.repository.WorkflowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WizardViewModel @Inject constructor(
    private val repository: WorkflowRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _selectedTrigger = MutableStateFlow<TriggerOption?>(null)
    val selectedTrigger = _selectedTrigger.asStateFlow()

    private val _selectedAction = MutableStateFlow<ActionOption?>(null)
    val selectedAction = _selectedAction.asStateFlow()
    
    private val _availableTriggers = MutableStateFlow<List<TriggerOption>>(emptyList())
    val availableTriggers = _availableTriggers.asStateFlow()
    
    private val _availableActions = MutableStateFlow<List<ActionOption>>(emptyList())
    val availableActions = _availableActions.asStateFlow()

    init {
        viewModelScope.launch {
            _availableTriggers.value = repository.getTriggers()
            _availableActions.value = repository.getActions()
        }
    }

    fun setName(value: String) { _name.value = value }
    fun selectTrigger(trigger: TriggerOption) { _selectedTrigger.value = trigger }
    fun selectAction(action: ActionOption) { _selectedAction.value = action }

    fun saveAutomation(onSuccess: () -> Unit) {
        val tId = _selectedTrigger.value?.id ?: return
        val aId = _selectedAction.value?.id ?: return
        viewModelScope.launch {
            repository.createWorkflow(_name.value, tId, aId)
            onSuccess()
        }
    }
}