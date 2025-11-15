package com.example.material3showcase.domain.usecase

import com.example.material3showcase.domain.model.CardModel
import com.example.material3showcase.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsConfigUseCase @Inject constructor(
    private val repository: ConfigRepository
) {
    operator fun invoke(): Flow<Result<List<CardModel>>> = repository.getCardsConfig()
}
