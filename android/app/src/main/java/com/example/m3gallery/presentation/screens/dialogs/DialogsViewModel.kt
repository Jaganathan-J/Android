package com.example.m3gallery.presentation.screens.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.core.Result
import com.example.m3gallery.core.UiEvent
import com.example.m3gallery.domain.usecase.GetDialogDemoItemsUseCase
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
class DialogsViewModel @Inject constructor(
    private val getDialogDemoItemsUseCase: GetDialogDemoItemsUseCase,
    private val triggerSnackbarUseCase: TriggerSnackbarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DialogsUiState(isLoading = true))
    val uiState: StateFlow<DialogsUiState> = _uiState.asStateFlow()

    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = getDialogDemoItemsUseCase()) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        items = result.data
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.throwable.message ?: "Failed to load dialogs"
                    )
                    _events.send(UiEvent.ShowSnackbar("Error loading dialog demos"))
                }
            }
        }
    }

    fun onShowConfirmation() {
        _uiState.value = _uiState.value.copy(showConfirmationDialog = true)
    }

    fun onConfirmationResult(confirmed: Boolean) {
        _uiState.value = _uiState.value.copy(showConfirmationDialog = false)
        viewModelScope.launch {
            val message = if (confirmed) "Confirmed" else "Cancelled"
            triggerSnackbar(message)
        }
    }

    fun onShowWarning() {
        _uiState.value = _uiState.value.copy(showWarningDialog = true)
    }

    fun onWarningDismiss() {
        _uiState.value = _uiState.value.copy(showWarningDialog = false)
    }

    fun onShowInput() {
        _uiState.value = _uiState.value.copy(showInputDialog = true)
    }

    fun onInputChange(value: String) {
        _uiState.value = _uiState.value.copy(inputValue = value)
    }

    fun onInputConfirm() {
        val value = _uiState.value.inputValue
        _uiState.value = _uiState.value.copy(showInputDialog = false, inputValue = "")
        viewModelScope.launch {
            triggerSnackbar("Input: $value")
        }
    }

    private suspend fun triggerSnackbar(message: String) {
        when (val result = triggerSnackbarUseCase(message)) {
            is Result.Success -> _events.send(UiEvent.ShowSnackbar(result.data.message))
            is Result.Error -> _events.send(UiEvent.ShowSnackbar("Failed to trigger snackbar"))
        }
    }
}
