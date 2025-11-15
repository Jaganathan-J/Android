package com.example.material3showcase.ui.screens.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.material3showcase.domain.usecase.GetCardsConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsConfig: GetCardsConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardsUiState(isLoading = true))
    val uiState: StateFlow<CardsUiState> = _uiState.asStateFlow()

    init {
        loadCards()
    }

    fun onEvent(event: CardsEvent) {
        when (event) {
            is CardsEvent.OnCardClick -> _uiState.update { it.copy(selectedId = event.id) }
            CardsEvent.Retry -> loadCards()
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            getCardsConfig().collect { result ->
                result.fold(
                    onSuccess = { list ->
                        _uiState.update { it.copy(isLoading = false, cards = list) }
                    },
                    onFailure = { throwable ->
                        _uiState.update { it.copy(isLoading = false, errorMessage = throwable.message) }
                    }
                )
            }
        }
    }
}
