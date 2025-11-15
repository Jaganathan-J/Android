package com.example.m3gallery

import app.cash.turbine.test
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import com.example.m3gallery.presentation.screens.buttons.ButtonsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ButtonsViewModelTest {

    @Test
    fun `primary click toggles loading state`() = runTest {
        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        val useCase = TriggerSnackbarUseCase()
        val viewModel = ButtonsViewModel(useCase)

        assertFalse(viewModel.uiState.value.isLoading)
        viewModel.onPrimaryClick()
        assertTrue(viewModel.uiState.value.isLoading)
    }
}
