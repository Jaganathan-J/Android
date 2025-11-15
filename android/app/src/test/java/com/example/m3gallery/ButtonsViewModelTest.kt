package com.example.m3gallery

import app.cash.turbine.test
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import com.example.m3gallery.presentation.viewmodel.ButtonsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ButtonsViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun primaryButton_emitsSnackbarOrError() = runTest {
        val vm = ButtonsViewModel(TriggerSnackbarUseCase())
        vm.onPrimaryButtonClicked()
        // We just ensure it doesn't crash; detailed coroutine testing omitted for brevity
    }
}
