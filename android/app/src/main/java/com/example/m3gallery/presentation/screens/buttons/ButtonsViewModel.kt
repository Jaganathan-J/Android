package com.example.m3gallery.presentation.screens.buttons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.domain.models.SnackMessage
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ButtonsViewModel @Inject constructor(
    private val triggerSnackbarUseCase: TriggerSnackbarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ButtonsUiState())
    val uiState: StateFlow<ButtonsUiState> = _uiState.asStateFlow()

    val snackbarEvents = triggerSnackbarUseCase.snackbarFlow

    fun onPrimaryClick() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            // Simulated long-running operation
            delay(1500)
            _uiState.value = _uiState.value.copy(isLoading = false)
            triggerSnackbarUseCase(SnackMessage("Primary action completed"))
        }
    }

    fun onErrorClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(error = "Simulated error while performing action")
            triggerSnackbarUseCase(SnackMessage("Something went wrong", actionLabel = "Retry"))
        }
    }

    fun onErrorConsumed() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
