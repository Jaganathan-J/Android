package com.example.autocompleteworkflow.domain.model

data class Workflow(
    val id: String,
    val name: String,
    val trigger: Integration,
    val action: Integration,
    val isEnabled: Boolean,
    val lastRunStatus: RunStatus,
    val lastRunTime: String
)

data class Integration(
    val id: String,
    val name: String,
    val description: String,
    val type: IntegrationType,
    val iconResName: String // Using simple generic names for mapping icon to UI
)

enum class IntegrationType {
    TRIGGER, ACTION
}

enum class RunStatus {
    SUCCESS, FAILED, PENDING, NONE
}

data class UserProfile(
    val name: String,
    val email: String
)