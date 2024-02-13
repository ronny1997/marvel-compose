package com.mpapps.marvelcompose.ui

import android.net.Uri
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.mpapps.marvelcompose.ui.NavigationScreen.Companion.CHARACTER_ID
import com.mpapps.marvelcompose.ui.views.players.PlayersScreen
import com.mpapps.marvelcompose.ui.views.questions.QuestionsScreen

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.PlayersScreen.route
    ) {
        composable(NavigationScreen.PlayersScreen.route) {
            PlayersScreen() {
                val characterJsonEncode = Uri.encode(Gson().toJson(it))
                navController.navigate(
                    NavigationScreen.QuestionsScreen.argumentWhitRout(characterJsonEncode)
                )
            }
        }
        composable(
            NavigationScreen.QuestionsScreen.route,
            enterTransition = {
                scaleIn(
                    animationSpec = tween(
                        1000, easing = LinearEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(900, easing = EaseIn),
                )
            },
            exitTransition = {
                scaleOut(
                    animationSpec = tween(
                        1000, easing = LinearEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(900, easing = EaseIn),
                )
            }
        ) { backStackEntry ->
            QuestionsScreen(
                backStackEntry.arguments?.getString(CHARACTER_ID)
            )
        }
    }
}