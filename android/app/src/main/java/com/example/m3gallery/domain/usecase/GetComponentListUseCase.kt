package com.example.m3gallery.domain.usecase

import com.example.m3gallery.domain.models.ComponentListItem
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import javax.inject.Inject

class GetComponentListUseCase @Inject constructor(
    private val repository: M3ComponentsRepository
) {
    suspend operator fun invoke(): List<ComponentListItem> {
        return repository.getComponentListItems()
    }
}
