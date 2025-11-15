package com.example.m3gallery.domain.usecase

import com.example.m3gallery.domain.models.ComponentItem
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComponentItemsUseCase @Inject constructor(
    private val repository: M3ComponentsRepository
) {
    operator fun invoke(): Flow<List<ComponentItem>> = repository.getComponentItems()
}
