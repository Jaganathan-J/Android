package com.example.m3gallery

import app.cash.turbine.test
import com.example.m3gallery.core.Result
import com.example.m3gallery.domain.model.ComponentItem
import com.example.m3gallery.domain.model.SnackMessage
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import com.example.m3gallery.domain.usecase.GetButtonDemoItemsUseCase
import com.example.m3gallery.domain.usecase.TriggerSnackbarUseCase
import com.example.m3gallery.presentation.screens.buttons.ButtonsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ButtonsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var scope: TestScope

    private lateinit var repository: FakeRepository

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        scope = TestScope(dispatcher)
        repository = FakeRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateLoadsItems() = scope.runTest {
        val viewModel = ButtonsViewModel(
            getButtonDemoItemsUseCase = GetButtonDemoItemsUseCase(repository),
            triggerSnackbarUseCase = TriggerSnackbarUseCase(repository)
        )

        dispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.uiState.value.items.isNotEmpty())
    }

    private class FakeRepository : M3ComponentsRepository {
        override suspend fun getButtonDemoItems(): Result<List<ComponentItem>> =
            Result.Success(listOf(ComponentItem("1", "Test", "Desc")))

        override suspend fun getCardDemoItems(): Result<List<ComponentItem>> =
            Result.Success(emptyList())

        override suspend fun getDialogDemoItems(): Result<List<ComponentItem>> =
            Result.Success(emptyList())

        override suspend fun triggerSnackbar(message: String): Result<SnackMessage> =
            Result.Success(SnackMessage(message))
    }
}
