package com.luxemarket.app.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.luxemarket.app.domain.model.Product

@JsonClass(generateAdapter = true)
data class FeedResponse(
    @Json(name = "data") val data: List<ProductDto>,
    @Json(name = "meta") val meta: MetaDto
)

@JsonClass(generateAdapter = true)
data class MetaDto(
    val page: Int,
    val has_next: Boolean
)

@JsonClass(generateAdapter = true)
data class ProductDto(
    val id: String,
    val title: String,
    val price: PriceDto,
    val images: List<ImageDto>,
    val condition: String,
    val seller: SellerDto
)

@JsonClass(generateAdapter = true)
data class PriceDto(
    val amount: Long,
    val currency: String,
    val display: String
)

@JsonClass(generateAdapter = true)
data class ImageDto(
    val url: String,
    val aspect_ratio: Float
)

@JsonClass(generateAdapter = true)
data class SellerDto(
    val id: String,
    val rating: Double
)

// Mapper extension
fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        priceFormatted = price.display,
        imageUrl = images.firstOrNull()?.url ?: "",
        condition = condition,
        sellerName = "Seller: ${seller.id}"
    )
}