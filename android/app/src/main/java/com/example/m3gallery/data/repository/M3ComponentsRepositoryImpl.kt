package com.example.m3gallery.data.repository

import com.example.m3gallery.domain.models.ComponentItem
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class M3ComponentsRepositoryImpl @Inject constructor() : M3ComponentsRepository {

    override fun getComponentItems(): Flow<List<ComponentItem>> = flow {
        // Simulate network delay
        delay(500)
        emit(
            listOf(
                ComponentItem(
                    id = "1",
                    title = "Outlined Button",
                    description = "Demonstrates a button with outlined style."
                ),
                ComponentItem(
                    id = "2",
                    title = "Modal Bottom Sheet",
                    description = "Shows how to use modal sheet for actions."
                ),
                ComponentItem(
                    id = "3",
                    title = "Cards",
                    description = "Elevated and filled cards with icons and text."
                )
            )
        )
    }
}
