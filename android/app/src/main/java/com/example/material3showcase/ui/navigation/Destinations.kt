package com.example.material3showcase.ui.navigation

sealed class Destination(val route: String, val label: String) {
    object GettingStarted : Destination("getting_started", "Getting Started")
    object Avatars : Destination("avatars", "Avatars")
    object Buttons : Destination("buttons", "Buttons")
    object Cards : Destination("cards", "Cards")
    object Dialogs : Destination("dialogs", "Dialogs")
    object TextFields : Destination("text_fields", "Text Fields")
}

val BottomNavDestinations = listOf(
    Destination.GettingStarted,
    Destination.Buttons,
    Destination.Avatars,
    Destination.Cards,
    Destination.Dialogs,
    Destination.TextFields
)
