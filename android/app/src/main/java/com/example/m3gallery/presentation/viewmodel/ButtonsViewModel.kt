package com.example.m3gallery.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.core.ui.UiEvent
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import com.example.m3gallery.presentation.screens.buttons.ButtonUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ButtonsViewModel @Inject constructor(
    private val triggerSnackbarUseCase: TriggerSnackbarUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ButtonUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    fun onPrimaryButtonClicked() {
        if (_state.value.isLoading) return
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            delay(1500)
            val isSuccess = (0..1).random() == 1
            if (isSuccess) {
                val snack = triggerSnackbarUseCase("Action completed", "OK")
                _events.emit(UiEvent.ShowSnackbar(snack.message, snack.actionLabel))
                _state.value = _state.value.copy(isLoading = false, error = null)
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Simulated network error. Please try again."
                )
                val snack = triggerSnackbarUseCase("Network error", "Retry")
                _events.emit(UiEvent.ShowSnackbar(snack.message, snack.actionLabel))
            }
        }
    }

    fun onElevatedButtonClicked() {
        viewModelScope.launch {
            val snack = triggerSnackbarUseCase("Elevated button clicked")
            _events.emit(UiEvent.ShowSnackbar(snack.message))
        }
    }

    fun onOutlinedButtonClicked() {
        viewModelScope.launch {
            _state.value = _state.value.copy(error = "Outlined button produced an error state.")
            val snack = triggerSnackbarUseCase("Error state simulated")
            _events.emit(UiEvent.ShowSnackbar(snack.message))
        }
    }
}
