package com.example.fitlife.domain.model

// Domain Logic Models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String?
)

data class IntegrationItem(
    val id: String,
    val name: String,
    val description: String? = null,
    val iconRes: Int? = null // Using Int for mocked drawable resources
)

data class Automation(
    val id: String,
    val name: String,
    val trigger: IntegrationItem,
    val action: IntegrationItem,
    val isActive: Boolean = true,
    val history: List<ExecutionHistory> = emptyList()
)

data class ExecutionHistory(
    val id: String,
    val automationName: String,
    val timestamp: String,
    val status: ExecutionStatus,
    val details: String
)

enum class ExecutionStatus {
    SUCCESS, FAILED, PENDING
}