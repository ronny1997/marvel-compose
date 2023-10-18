package com.mpapps.marvelcompose.ui

sealed class NavigationScreen(val route: String) {
    object PlayersScreen : NavigationScreen("playersScreen")
    object QuestionsScreen : NavigationScreen("questionsScreen")
}