package com.example.m3gallery.presentation.screens.buttons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.core.Result
import com.example.m3gallery.core.UiEvent
import com.example.m3gallery.domain.usecase.GetButtonDemoItemsUseCase
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ButtonsViewModel @Inject constructor(
    private val getButtonDemoItemsUseCase: GetButtonDemoItemsUseCase,
    private val triggerSnackbarUseCase: TriggerSnackbarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ButtonsUiState(isLoading = true))
    val uiState: StateFlow<ButtonsUiState> = _uiState.asStateFlow()

    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = getButtonDemoItemsUseCase()) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        items = result.data,
                        errorMessage = null
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.throwable.message ?: "Failed to load button demos"
                    )
                    _events.send(UiEvent.ShowSnackbar("Error loading buttons"))
                }
            }
        }
    }

    fun onPrimaryButtonClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isPrimaryButtonLoading = true)
            when (val result = triggerSnackbarUseCase("Primary button clicked")) {
                is Result.Success -> {
                    _events.send(UiEvent.ShowSnackbar(result.data.message))
                }
                is Result.Error -> {
                    _events.send(UiEvent.ShowSnackbar("Failed to trigger snackbar"))
                }
            }
            _uiState.value = _uiState.value.copy(isPrimaryButtonLoading = false)
        }
    }

    fun onToggleEnabled() {
        val newEnabled = !_uiState.value.isEnabled
        _uiState.value = _uiState.value.copy(isEnabled = newEnabled)
    }
}
