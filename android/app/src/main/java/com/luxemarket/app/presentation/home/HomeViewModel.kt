package com.luxemarket.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luxemarket.app.domain.model.Product
import com.luxemarket.app.domain.usecase.GetFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val products: List<Product>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchFeed()
    }

    fun fetchFeed() {
        viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }
            getFeedUseCase().fold(
                onSuccess = { products ->
                    _uiState.update { HomeUiState.Success(products) }
                },
                onFailure = { error ->
                    _uiState.update { HomeUiState.Error(error.message ?: "Unknown error") }
                }
            )
        }
    }
}