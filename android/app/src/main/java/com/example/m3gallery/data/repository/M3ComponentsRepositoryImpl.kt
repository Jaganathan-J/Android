package com.example.m3gallery.data.repository

import com.example.m3gallery.domain.models.ComponentListItem
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class M3ComponentsRepositoryImpl @Inject constructor() : M3ComponentsRepository {
    override suspend fun getComponentList(): List<ComponentListItem> {
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
                description = "Shows elevated card with image and text."
            ),
            ComponentListItem(
                id = "4",
                title = "Dialogs",
                description = "Confirmation, warning and input dialogs."
            )
        )
    }
}
