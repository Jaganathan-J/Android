package com.example.material3showcase.domain.usecase

import com.example.material3showcase.domain.model.DialogModel
import com.example.material3showcase.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDialogsConfigUseCase @Inject constructor(
    private val repository: ConfigRepository
) {
    operator fun invoke(): Flow<Result<List<DialogModel>>> = repository.getDialogsConfig()
}
