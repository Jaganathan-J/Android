package com.example.dynamictheme.domain.model

data class SyncInfo(
    val lastSyncTimeMillis: Long?,
    val themeVersion: String?
)