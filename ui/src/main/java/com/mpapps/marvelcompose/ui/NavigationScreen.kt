package com.mpapps.marvelcompose.ui

sealed class NavigationScreen(val route: String) {

    companion object {
        const val CHARACTER_ID = "userId"
    }

    object PlayersScreen : NavigationScreen(Screen.CHARACTERS.name)
    object QuestionsScreen : NavigationScreen(Screen.CHARACTERS_DETAIL.name + "/{$CHARACTER_ID}") {
        fun argumentWhitRout(argument: String) = "$route".replace("{$CHARACTER_ID}", argument)
    }
}

enum class Screen {
    CHARACTERS,
    CHARACTERS_DETAIL,
}