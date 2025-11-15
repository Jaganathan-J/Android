package com.example.m3gallery.presentation.screens.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m3gallery.domain.models.SnackMessage
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val triggerSnackbarUseCase: TriggerSnackbarUseCase
) : ViewModel() {

    val snackbarEvents = triggerSnackbarUseCase.snackbarFlow

    fun onCardClicked(type: String) {
        viewModelScope.launch {
            triggerSnackbarUseCase(SnackMessage("$type clicked"))
        }
    }
}
