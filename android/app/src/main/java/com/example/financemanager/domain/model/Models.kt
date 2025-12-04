package com.example.financemanager.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class IntegrationItem(
    val id: String,
    val name: String,
    val type: IntegrationType,
    val iconName: String // Simple string identifier for icon mapping
)

enum class IntegrationType {
    TRIGGER, ACTION
}

data class Workflow(
    val id: String,
    val name: String,
    val trigger: IntegrationItem,
    val action: IntegrationItem,
    val createdAt: Long
)

data class ExecutionLog(
    val id: String,
    val workflowName: String,
    val status: ExecutionStatus,
    val timestamp: String,
    val message: String
)

enum class ExecutionStatus {
    SUCCESS, FAILED, PENDING
}