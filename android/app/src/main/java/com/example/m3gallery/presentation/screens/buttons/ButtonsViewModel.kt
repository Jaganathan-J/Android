package com.example.m3gallery.presentation.screens.buttons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.core.UiEvent
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ButtonsViewModel @Inject constructor(
    private val triggerSnackbarUseCase: TriggerSnackbarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ButtonUiState())
    val uiState: StateFlow<ButtonUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onPrimaryButtonClick() {
        if (_uiState.value.isLoading) return
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            delay(1500)
            val isError = System.currentTimeMillis() % 2L == 0L
            if (isError) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Simulated network error."
                )
                val snack = triggerSnackbarUseCase("Button failed", "Retry")
                _uiEvents.send(UiEvent.ShowSnackbar(snack.message, snack.actionLabel))
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = null)
                val snack = triggerSnackbarUseCase("Action completed")
                _uiEvents.send(UiEvent.ShowSnackbar(snack.message, snack.actionLabel))
            }
        }
    }

    fun onToggleEnabled() {
        _uiState.value = _uiState.value.copy(isEnabled = !_uiState.value.isEnabled)
    }
}
