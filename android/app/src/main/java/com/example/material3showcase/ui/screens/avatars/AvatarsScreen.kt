package com.example.material3showcase.ui.screens.avatars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.material3showcase.domain.model.AvatarModel
import com.example.material3showcase.ui.theme.avatarBackground
import com.example.material3showcase.ui.theme.avatarText

@Composable
fun AvatarsScreen(
    uiState: AvatarsUiState,
    onEvent: (AvatarsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Avatars", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = "Image- and text-based avatars with optional status badges.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.padding(8.dp))

        if (uiState.isLoading) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator()
            }
        } else {
            uiState.errorMessage?.let { msg ->
                Text(text = msg, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.padding(4.dp))
                OutlinedButton(onClick = { onEvent(AvatarsEvent.Retry) }) {
                    Text("Retry")
                }
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(uiState.avatars) { avatar ->
                    AvatarItem(
                        model = avatar,
                        selected = uiState.selectedId == avatar.id,
                        onClick = { onEvent(AvatarsEvent.OnAvatarClick(avatar.id)) }
                    )
                }
            }

            AnimatedVisibility(visible = uiState.selectedId != null) {
                uiState.selectedId?.let { id ->
                    Spacer(modifier = Modifier.padding(8.dp))
                    val selected = uiState.avatars.firstOrNull { it.id == id }
                    selected?.let {
                        Text("Selected: ${it.name}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Composable
private fun AvatarItem(model: AvatarModel, selected: Boolean, onClick: () -> Unit) {
    val scale by animateFloatAsState(targetValue = if (selected) 1.1f else 1f, animationSpec = spring(), label = "avatarScale")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .scale(scale)
                    .shadow(if (selected) 6.dp else 2.dp, CircleShape)
                    .background(avatarBackground, CircleShape)
                    .border(width = if (selected) 2.dp else 0.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
            ) {
                Text(
                    text = initials(model.name),
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge,
                    color = avatarText,
                    textAlign = TextAlign.Center
                )
            }
            if (model.hasBadge) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.background, CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Card(
            onClick = onClick,
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Text(
                text = model.name,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private fun initials(name: String): String {
    val parts = name.split(" ").filter { it.isNotBlank() }
    return when {
        parts.size >= 2 -> "${parts[0].first()}${parts[1].first()}".uppercase()
        parts.isNotEmpty() -> parts[0].take(2).uppercase()
        else -> "?"
    }
}
