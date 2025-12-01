package com.luxemarket.app.domain.repository

import com.luxemarket.app.domain.model.Product

interface ProductRepository {
    suspend fun getHomeFeed(): Result<List<Product>>
}