package com.example.automationoverflow.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String, 
    val avatarUrl: String? = null
)

data class Trigger(
    val id: String,
    val name: String,
    val description: String,
    val provider: String // e.g., "slack", "google"
)

data class Action(
    val id: String,
    val name: String,
    val description: String,
    val provider: String
)

data class Workflow(
    val id: String,
    val name: String,
    val trigger: Trigger,
    val action: Action,
    val createdAt: Long
)

data class HistoryItem(
    val id: String,
    val workflowName: String,
    val status: String, // "Success", "Running", "Failed"
    val executedAt: Long
)