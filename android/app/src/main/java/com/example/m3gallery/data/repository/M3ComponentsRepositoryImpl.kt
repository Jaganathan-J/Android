package com.example.m3gallery.data.repository

import com.example.m3gallery.domain.models.ComponentListItem
import com.example.m3gallery.domain.models.SnackMessage
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class M3ComponentsRepositoryImpl @Inject constructor() : M3ComponentsRepository {

    override suspend fun getComponentListItems(): List<ComponentListItem> {
        delay(300)
        return listOf(
            ComponentListItem(
                id = "1",
                title = "Outlined Button",
                description = "Demonstrates a button with outlined style."
            ),
            ComponentListItem(
                id = "2",
                title = "Modal Bottom Sheet",
                description = "Shows how to use modal sheet for actions."
            ),
            ComponentListItem(
                id = "3",
                title = "Elevated Card",
                description = "Card with elevation and clickable feedback."
            )
        )
    }

    override suspend fun getSampleSnackMessage(): SnackMessage {
        delay(200)
        return SnackMessage(
            id = "snack_1",
            message = "This is a sample snackbar from use case.",
            actionLabel = "Dismiss"
        )
    }
}
