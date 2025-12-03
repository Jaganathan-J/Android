package com.automationoverflow.app.domain.models

data class WorkflowItem(
    val id: String,
    val name: String,
    val triggerName: String,
    val actionName: String,
    val status: String,
    val timestamp: String
)

data class TriggerOption(
    val id: String,
    val name: String,
    val description: String,
    val iconType: String // Simple mapper type
)

data class ActionOption(
    val id: String,
    val name: String,
    val description: String,
    val iconType: String
)

data class UserProfile(
    val name: String,
    val email: String
)