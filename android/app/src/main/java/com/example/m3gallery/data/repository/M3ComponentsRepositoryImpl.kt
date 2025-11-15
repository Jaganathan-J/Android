package com.example.m3gallery.data.repository

import com.example.m3gallery.domain.models.ComponentListItem
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import javax.inject.Inject

class M3ComponentsRepositoryImpl @Inject constructor() : M3ComponentsRepository {

    override suspend fun getComponentListItems(): List<ComponentListItem> {
        // Simulated static data, could be replaced by remote/local data sources later.
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
                description = "Illustrates elevated surface and shadow states."
            ),
            ComponentListItem(
                id = "4",
                title = "Confirmation Dialog",
                description = "Standard confirmation dialog with primary action."
            )
        )
    }
}
