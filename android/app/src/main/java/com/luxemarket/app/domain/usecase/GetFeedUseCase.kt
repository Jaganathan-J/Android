package com.luxemarket.app.domain.usecase

import com.luxemarket.app.domain.model.Product
import com.luxemarket.app.domain.repository.ProductRepository
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return repository.getHomeFeed()
    }
}