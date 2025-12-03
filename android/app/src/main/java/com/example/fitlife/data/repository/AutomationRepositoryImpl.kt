package com.example.fitlife.data.repository

import com.example.fitlife.domain.model.*
import com.example.fitlife.domain.repository.AutomationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class AutomationRepositoryImpl @Inject constructor() : AutomationRepository {

    private val mockHistory = mutableListOf(
        ExecutionHistory("1", "Reignll.text", "10:43 AM", ExecutionStatus.SUCCESS, "Processed successfully"),
        ExecutionHistory("2", "Catch Error", "Yesterday", ExecutionStatus.FAILED, "Timeout occurred"),
        ExecutionHistory("3", "Auto-Reply", "Oct 24", ExecutionStatus.SUCCESS, "Sent to 3 users")
    )

    override fun getTriggers(): List<IntegrationItem> = listOf(
        IntegrationItem("t1", "Slack", "New Message posted", 0),
        IntegrationItem("t2", "Gmail", "New Email received", 0),
        IntegrationItem("t3", "Calendar", "Event starts", 0)
    )

    override fun getActions(): List<IntegrationItem> = listOf(
        IntegrationItem("a1", "Google Drive", "Save file", 0),
        IntegrationItem("a2", "Notification", "Send push alert", 0),
        IntegrationItem("a3", "Hue Lights", "Turn on lights", 0),
        IntegrationItem("a4", "Spotify", "Play playlist", 0)
    )

    override suspend fun saveAutomation(name: String, trigger: IntegrationItem, action: IntegrationItem) {
        delay(1000) // Simulate network
        // In a real app, save to backend room
        mockHistory.add(0, 
            ExecutionHistory(
                UUID.randomUUID().toString(), 
                name, 
                "Just Now", 
                ExecutionStatus.PENDING, 
                "Initializing..."
            )
        )
    }

    override fun getHistory(): Flow<List<ExecutionHistory>> = flow {
        emit(mockHistory)
    }

    override suspend fun toggleAutomation(id: String, active: Boolean) {
        delay(300)
        // Toggle logic
    }
}