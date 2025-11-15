package com.example.m3gallery.domain.usecase

import com.example.m3gallery.core.Result
import com.example.m3gallery.domain.model.ComponentItem
import com.example.m3gallery.domain.repository.M3ComponentsRepository
import javax.inject.Inject

class GetDialogDemoItemsUseCase @Inject constructor(
    private val repository: M3ComponentsRepository
) {
    suspend operator fun invoke(): Result<List<ComponentItem>> = repository.getDialogDemoItems()
}
