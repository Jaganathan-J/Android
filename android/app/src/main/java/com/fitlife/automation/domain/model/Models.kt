package com.fitlife.automation.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Trigger(
    val id: String,
    val name: String,
    val serviceName: String,
    val iconVector: ImageVector,
    val color: Color
)

data class Action(
    val id: String,
    val name: String,
    val serviceName: String,
    val iconVector: ImageVector,
    val color: Color
)

data class Workflow(
    val id: String,
    val name: String,
    val triggerName: String,
    val actionName: String,
    val isActive: Boolean,
    val lastRun: String,
    val status: String // "Success" or "Failed"
)

data class User(
    val name: String,
    val email: String
)