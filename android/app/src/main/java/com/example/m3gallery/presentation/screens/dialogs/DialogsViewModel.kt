package com.example.m3gallery.presentation.screens.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.domain.models.SnackMessage
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DialogsUiState(
    val showConfirmation: Boolean = false
)

@HiltViewModel
class DialogsViewModel @Inject constructor(
    private val triggerSnackbarUseCase: TriggerSnackbarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DialogsUiState())
    val uiState: StateFlow<DialogsUiState> = _uiState.asStateFlow()

    val snackbarEvents = triggerSnackbarUseCase.snackbarFlow

    fun onShowDialogClick() {
        _uiState.value = _uiState.value.copy(showConfirmation = true)
    }

    fun onDialogDismiss() {
        _uiState.value = _uiState.value.copy(showConfirmation = false)
    }

    fun onDialogConfirm() {
        _uiState.value = _uiState.value.copy(showConfirmation = false)
        viewModelScope.launch {
            triggerSnackbarUseCase(SnackMessage("Confirmed"))
        }
    }
}
