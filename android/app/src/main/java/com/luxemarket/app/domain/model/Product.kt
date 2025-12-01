package com.luxemarket.app.domain.model

data class Product(
    val id: String,
    val title: String,
    val priceFormatted: String,
    val imageUrl: String,
    val condition: String,
    val sellerName: String
)