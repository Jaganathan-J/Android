package com.example.m3gallery.domain.repository

import com.example.m3gallery.domain.models.ComponentListItem

interface M3ComponentsRepository {
    suspend fun getComponentListItems(): List<ComponentListItem>
}
