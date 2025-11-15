package com.example.material3showcase.ui.screens.gettingstarted

import com.example.material3showcase.ui.navigation.Destination

data class GettingStartedUiState(
    val description: String = "Explore Material 3 components implemented with Jetpack Compose.",
    val destinations: List<Destination> = listOf(
        Destination.Buttons,
        Destination.Avatars,
        Destination.Cards,
        Destination.Dialogs,
        Destination.TextFields
    )
)
