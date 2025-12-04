package com.example.autoflow.domain.model

data class ExecutionLog(
    val id: String,
    val workflowName: String,
    val status: ExecutionStatus,
    val timestamp: Long
)

enum class ExecutionStatus {
    SUCCESS, FAILED
}