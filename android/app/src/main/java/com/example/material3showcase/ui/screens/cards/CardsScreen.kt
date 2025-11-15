package com.example.material3showcase.ui.screens.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.material3showcase.domain.model.CardModel

@Composable
fun CardsScreen(
    uiState: CardsUiState,
    onEvent: (CardsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Cards", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Elevated and outlined cards that react to interaction.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.errorMessage != null -> {
                Text(text = uiState.errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { onEvent(CardsEvent.Retry) }) {
                    Text("Retry")
                }
            }
            else -> {
                LazyColumn(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                    items(uiState.cards) { card ->
                        CardItem(
                            model = card,
                            selected = uiState.selectedId == card.id,
                            onClick = { onEvent(CardsEvent.OnCardClick(card.id)) }
                        )
                    }
                }
                AnimatedVisibility(visible = uiState.selectedId != null) {
                    uiState.selectedId?.let { id ->
                        Spacer(modifier = Modifier.height(12.dp))
                        val selected = uiState.cards.firstOrNull { it.id == id }
                        selected?.let {
                            Text("Selected card: ${it.title}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CardItem(model: CardModel, selected: Boolean, onClick: () -> Unit) {
    val elevation by animateDpAsState(
        targetValue = if (selected) 8.dp else 2.dp,
        animationSpec = tween(200),
        label = "cardElevation"
    )

    val shape = CardDefaults.shape

    val modifier = Modifier
        .fillMaxWidth()

    val cardColors = if (model.outlined) CardDefaults.outlinedCardColors() else CardDefaults.cardColors()

    val cardBorder = if (model.outlined) CardDefaults.outlinedCardBorder() else null

    Card(
        onClick = onClick,
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = cardColors,
        border = cardBorder,
        shape = shape
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = model.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = model.description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
