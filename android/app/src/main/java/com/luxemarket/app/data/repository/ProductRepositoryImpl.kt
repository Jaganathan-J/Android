package com.luxemarket.app.data.repository

import com.luxemarket.app.data.remote.LuxeApi
import com.luxemarket.app.data.remote.dto.toDomain
import com.luxemarket.app.domain.model.Product
import com.luxemarket.app.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.io.IOException

class ProductRepositoryImpl @Inject constructor(
    private val api: LuxeApi
) : ProductRepository {

    override suspend fun getHomeFeed(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            // Simulate API delay for UX demonstration
            // In production, removing delay
            val response = api.getFeed()
            Result.success(response.data.map { it.toDomain() })
        } catch (e: IOException) {
            Result.failure(Exception("Network error. Please check your connection."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}