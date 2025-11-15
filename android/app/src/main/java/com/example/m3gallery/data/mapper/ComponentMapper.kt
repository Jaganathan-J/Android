package com.example.m3gallery.data.mapper

import com.example.m3gallery.data.remote.dto.ComponentDto
import com.example.m3gallery.domain.model.ComponentItem

fun ComponentDto.toDomain(): ComponentItem = ComponentItem(
    id = id,
    title = title,
    description = description
)
