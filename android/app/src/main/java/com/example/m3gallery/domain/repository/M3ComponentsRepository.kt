package com.example.m3gallery.domain.repository

import com.example.m3gallery.domain.models.ComponentItem
import kotlinx.coroutines.flow.Flow

interface M3ComponentsRepository {
    fun getComponentItems(): Flow<List<ComponentItem>>
}
