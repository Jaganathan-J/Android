package com.fitlife.automation.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import com.fitlife.automation.domain.model.*
import com.fitlife.automation.domain.repository.AuthRepository
import com.fitlife.automation.domain.repository.WorkflowRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAuthRepository @Inject constructor() : AuthRepository {
    private val _user = MutableStateFlow<User?>(null)

    override suspend fun login(email: String, pass: String): Result<User> {
        delay(1500) // Simulate net
        if (email.isNotEmpty() && pass.length > 5) {
            val user = User("Alex", email)
            _user.value = user
            return Result.success(user)
        }
        return Result.failure(Exception("Invalid credentials"))
    }

    override fun getUser(): Flow<User?> = _user.asStateFlow()

    override suspend fun logout() {
        _user.value = null
    }
}

@Singleton
class MockWorkflowRepository @Inject constructor() : WorkflowRepository {
    private val history = MutableStateFlow<List<Workflow>>(listOf(
        Workflow("1", "Daily Standup", "Git Commit", "Slack Message", true, "2 mins ago", "Success"),
        Workflow("2", "Client Forwarding", "New Email", "Jira Ticket", true, "1 hour ago", "Success"),
        Workflow("3", "Server Pings", "Webhook", "Email Admin", false, "5 hours ago", "Failed")
    ))

    override suspend fun getTriggers(): List<Trigger> = listOf(
        Trigger("t1", "New Email", "Gmail", Icons.Default.Email, Color(0xFFEA4335)),
        Trigger("t2", "New Commit", "GitHub", Icons.Default.Code, Color(0xFF333333)),
        Trigger("t3", "Message", "Slack", Icons.Default.ChatBubble, Color(0xFF4A154B))
    )

    override suspend fun getActions(): List<Action> = listOf(
        Action("a1", "Send Message", "Slack", Icons.Default.Send, Color(0xFF4A154B)),
        Action("a2", "Create Ticket", "Jira", Icons.Default.Assignment, Color(0xFF0052CC)),
        Action("a3", "Save File", "Drive", Icons.Default.CloudUpload, Color(0xFF1EA362))
    )

    override suspend fun createWorkflow(name: String, triggerId: String, actionId: String): Result<Boolean> {
        delay(2000)
        val newWorkflow = Workflow(
             id = "w_${System.currentTimeMillis()}",
             name = name,
             triggerName = "Selected Trigger",
             actionName = "Selected Action",
             isActive = true,
             lastRun = "Just now",
             status = "Success"
        )
        history.value = listOf(newWorkflow) + history.value
        return Result.success(true)
    }

    override fun getHistory(): Flow<List<Workflow>> = history.asStateFlow()
}