package com.example.material3showcase.ui.screens.gettingstarted

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GettingStartedViewModel @Inject constructor() : ViewModel() {
    val uiState = GettingStartedUiState()
}
