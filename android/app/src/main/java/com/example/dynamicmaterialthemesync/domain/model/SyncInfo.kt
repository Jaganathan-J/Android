package com.example.dynamicmaterialthemesync.domain.model

data class SyncInfo(
    val lastSyncTimeMillis: Long?,
    val lastThemeVersion: String?
)