package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.*
import com.example.fitlife.domain.repository.AutomationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AutomationRepositoryImpl @Inject constructor() : AutomationRepository {

    private val workflows = MutableStateFlow<List<Workflow>>(emptyList())

    private val triggers = listOf(
        Trigger("t1", "Receive Email", "When an email is received", "email"),
        Trigger("t2", "Time of Day", "Every day at specific time", "schedule"),
        Trigger("t3", "New File", "When file added to Dropbox", "folder")
    )

    private val actions = listOf(
        ActionOption("a1", "Send Slack Message", "Post to #general", "slack"),
        ActionOption("a2", "Turn on Lights", "Hue Living Room", "lightbulb"),
        ActionOption("a3", "Save to Drive", "Save attachment", "cloud_upload")
    )

    private val history = listOf(
        ExecutionLog("h1", "Email to Slack", "10:00 AM", ExecutionStatus.SUCCESS, "Sent"),
        ExecutionLog("h2", "Morning Lights", "07:00 AM", ExecutionStatus.SUCCESS, "on"),
        ExecutionLog("h3", "Backup Data", "Yesterday", ExecutionStatus.FAILURE, "Network error")
    )

    override suspend fun getTriggers(): List<Trigger> {
        delay(500)
        return triggers
    }

    override suspend fun getActions(): List<ActionOption> {
        delay(500)
        return actions
    }

    override suspend fun createWorkflow(name: String, triggerId: String, actionId: String): Result<Boolean> {
        delay(1500)
        val t = triggers.find { it.id == triggerId } ?: return Result.failure(Exception("Trigger not found"))
        val a = actions.find { it.id == actionId } ?: return Result.failure(Exception("Action not found"))
        
        val newWf = Workflow(
            id = java.util.UUID.randomUUID().toString(),
            name = name,
            trigger = t,
            action = a
        )
        workflows.value = workflows.value + newWf
        return Result.success(true)
    }

    override fun getWorkflows(): Flow<List<Workflow>> = workflows

    override suspend fun getHistory(): List<ExecutionLog> {
        delay(800)
        return history
    }
}