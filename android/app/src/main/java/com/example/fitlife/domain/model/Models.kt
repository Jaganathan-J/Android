package com.example.fitlife.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val avatarUrl: String?
)

data class Trigger(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconName: String // E.g., "email", "clock"
)

data class ActionOption(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconName: String // E.g., "slack", "hue"
)

data class Workflow(
    val id: String,
    val name: String,
    val trigger: Trigger,
    val action: ActionOption,
    val isActive: Boolean = true
)

enum class ExecutionStatus { SUCCESS, FAILURE }

data class ExecutionLog(
    val id: String,
    val workflowName: String,
    val timestamp: String,
    val status: ExecutionStatus,
    val message: String
)