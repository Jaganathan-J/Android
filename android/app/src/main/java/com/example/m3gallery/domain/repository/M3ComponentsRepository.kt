package com.example.m3gallery.domain.repository

import com.example.m3gallery.core.Result
import com.example.m3gallery.domain.model.ComponentItem
import com.example.m3gallery.domain.model.SnackMessage

interface M3ComponentsRepository {
    suspend fun getButtonDemoItems(): Result<List<ComponentItem>>
    suspend fun getCardDemoItems(): Result<List<ComponentItem>>
    suspend fun getDialogDemoItems(): Result<List<ComponentItem>>
    suspend fun triggerSnackbar(message: String): Result<SnackMessage>
}
