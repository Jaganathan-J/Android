package com.example.material3showcase.domain.usecase

import com.example.material3showcase.domain.repository.ConfigRepository
import javax.inject.Inject

class SyncConfigUseCase @Inject constructor(
    private val repository: ConfigRepository
) {
    suspend operator fun invoke() {
        // Reserved for future synchronization logic with backend-driven UI
        // Currently unused as configs are pulled per-feature.
    }
}
