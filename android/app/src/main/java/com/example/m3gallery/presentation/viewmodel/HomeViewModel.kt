package com.example.m3gallery.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.m3gallery.presentation.screens.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val state: HomeUiState = HomeUiState()
}
