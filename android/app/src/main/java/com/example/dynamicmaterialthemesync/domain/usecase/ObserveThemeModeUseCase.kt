package com.example.dynamicmaterialthemesync.domain.usecase

import com.example.dynamicmaterialthemesync.core.theme.ThemeMode
import com.example.dynamicmaterialthemesync.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveThemeModeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    operator fun invoke(): Flow<ThemeMode> = repository.observeThemeMode()
}