package com.example.autoflow.domain.model

data class Workflow(
    val id: String,
    val name: String,
    val trigger: ServiceItem,
    val action: ServiceItem,
    val isActive: Boolean = true
)