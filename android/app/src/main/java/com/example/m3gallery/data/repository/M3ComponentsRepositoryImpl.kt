package com.example.m3gallery.data.repository

import com.example.m3gallery.core.Result
import com.example.m3gallery.domain.model.ComponentItem
import com.example.m3gallery.domain.model.SnackMessage
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class M3ComponentsRepositoryImpl @Inject constructor() : M3ComponentsRepository {

    override suspend fun getButtonDemoItems(): Result<List<ComponentItem>> {
        delay(300)
        val items = listOf(
            ComponentItem(
                id = "1",
                title = "Filled Button",
                description = "Demonstrates a filled button with primary color."
            ),
            ComponentItem(
                id = "2",
                title = "Outlined Button",
                description = "Demonstrates a button with outlined style."
            ),
            ComponentItem(
                id = "3",
                title = "Text Button",
                description = "Low-emphasis text-only button."
            ),
            ComponentItem(
                id = "4",
                title = "Elevated Button",
                description = "Button with elevation and tonal emphasis."
            )
        )
        return Result.Success(items)
    }

    override suspend fun getCardDemoItems(): Result<List<ComponentItem>> {
        delay(300)
        val items = listOf(
            ComponentItem(
                id = "10",
                title = "Elevated Card",
                description = "Card that lifts above the surface on interaction."
            ),
            ComponentItem(
                id = "11",
                title = "Filled Card",
                description = "Card with container color for emphasis."
            )
        )
        return Result.Success(items)
    }

    override suspend fun getDialogDemoItems(): Result<List<ComponentItem>> {
        delay(300)
        val items = listOf(
            ComponentItem(
                id = "20",
                title = "Confirmation Dialog",
                description = "Dialog requesting a confirmation action."
            ),
            ComponentItem(
                id = "21",
                title = "Warning Dialog",
                description = "Dialog emphasizing a destructive action."
            ),
            ComponentItem(
                id = "22",
                title = "Input Dialog",
                description = "Dialog collecting a short text input."
            )
        )
        return Result.Success(items)
    }

    override suspend fun triggerSnackbar(message: String): Result<SnackMessage> {
        delay(150)
        return Result.Success(SnackMessage(message = message))
    }
}
