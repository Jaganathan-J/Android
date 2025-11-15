package com.example.material3showcase.domain.usecase

import com.example.material3showcase.domain.model.AvatarModel
import com.example.material3showcase.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAvatarsConfigUseCase @Inject constructor(
    private val repository: ConfigRepository
) {
    operator fun invoke(): Flow<Result<List<AvatarModel>>> = repository.getAvatarsConfig()
}
