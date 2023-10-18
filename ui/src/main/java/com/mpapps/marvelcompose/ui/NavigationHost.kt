package com.mpapps.marvelcompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mpapps.marvelcompose.ui.views.players.PlayersScreen
import com.mpapps.marvelcompose.ui.views.questions.QuestionsScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.PlayersScreen.route
    ) {
        composable(NavigationScreen.PlayersScreen.route) {
            PlayersScreen()
        }
        composable(NavigationScreen.QuestionsScreen.route) {
            QuestionsScreen()
        }
    }
}