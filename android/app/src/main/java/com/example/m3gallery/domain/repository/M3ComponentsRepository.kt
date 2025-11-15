package com.example.m3gallery.domain.repository

import com.example.m3gallery.domain.models.ComponentListItem
import com.example.m3gallery.domain.models.SnackMessage

interface M3ComponentsRepository {
    suspend fun getComponentListItems(): List<ComponentListItem>
    suspend fun getSampleSnackMessage(): SnackMessage
}
