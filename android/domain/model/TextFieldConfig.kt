package com.example.material3showcase.domain.model

data class TextFieldConfig(
    val id: String,
    val label: String,
    val placeholder: String,
    val isPassword: Boolean = false,
    val supportingText: String? = null,
    val errorText: String? = null
)
