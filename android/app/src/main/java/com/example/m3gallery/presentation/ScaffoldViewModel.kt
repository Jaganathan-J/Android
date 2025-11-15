package com.example.m3gallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.core.UiEvent
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class ScaffoldEvent {
    data object OnMenuClick : ScaffoldEvent()
    data object OnDrawerClose : ScaffoldEvent()
    data class ShowSnackbar(val message: String) : ScaffoldEvent()
}

data class ScaffoldUiState(
    val isDrawerOpen: Boolean = false
)

@HiltViewModel
class ScaffoldViewModel @Inject constructor(
    private val triggerSnackbarUseCase: TriggerSnackbarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScaffoldUiState())
    val uiState: StateFlow<ScaffoldUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvent(event: ScaffoldEvent) {
        when (event) {
            ScaffoldEvent.OnMenuClick -> {
                _uiState.value = _uiState.value.copy(isDrawerOpen = true)
            }

            ScaffoldEvent.OnDrawerClose -> {
                _uiState.value = _uiState.value.copy(isDrawerOpen = false)
            }

            is ScaffoldEvent.ShowSnackbar -> {
                val snack = triggerSnackbarUseCase(event.message)
                viewModelScope.launch {
                    _uiEvents.send(UiEvent.ShowSnackbar(snack.message, snack.actionLabel))
                }
            }
        }
    }
}
