package com.example.m3gallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import com.example.m3gallery.presentation.uistates.ButtonUiState
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

    private val _uiState = MutableStateFlow(ButtonUiState())
    val uiState: StateFlow<ButtonUiState> = _uiState.asStateFlow()

    fun onPrimaryButtonClick() {
        if (_uiState.value.isLoading) return
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                delay(1000)
                val snack = triggerSnackbarUseCase()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    lastActionMessage = snack.message,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to perform action"
                )
            }
        }
    }

    fun onToggleEnabled() {
        _uiState.value = _uiState.value.copy(isEnabled = !_uiState.value.isEnabled)
    }

    fun simulateError() {
        _uiState.value = _uiState.value.copy(error = "Simulated network error")
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
