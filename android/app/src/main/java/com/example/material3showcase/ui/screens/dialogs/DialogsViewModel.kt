package com.example.material3showcase.ui.screens.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.material3showcase.domain.usecase.GetDialogsConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DialogsViewModel @Inject constructor(
    private val getDialogsConfig: GetDialogsConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DialogsUiState(isLoading = true))
    val uiState: StateFlow<DialogsUiState> = _uiState.asStateFlow()

    init {
        loadDialogs()
    }

    fun onEvent(event: DialogsEvent) {
        when (event) {
            is DialogsEvent.ShowDialog -> _uiState.update { it.copy(activeDialog = event.dialog) }
            DialogsEvent.Dismiss -> _uiState.update { it.copy(activeDialog = null) }
            is DialogsEvent.Confirm -> {
                _uiState.update {
                    it.copy(
                        activeDialog = null,
                        lastAction = "Confirmed dialog ${event.dialogId}"
                    )
                }
            }
            DialogsEvent.Retry -> loadDialogs()
        }
    }

    private fun loadDialogs() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            getDialogsConfig().collect { result ->
                result.fold(
                    onSuccess = { list ->
                        _uiState.update { it.copy(isLoading = false, dialogs = list) }
                    },
                    onFailure = { throwable ->
                        _uiState.update { it.copy(isLoading = false, errorMessage = throwable.message) }
                    }
                )
            }
        }
    }
}
