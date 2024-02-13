package com.mpapps.marvelcompose.ui

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
import com.mpapps.marvelcompose.ui.views.character.CharacterDetailDestination
import com.mpapps.marvelcompose.ui.views.character.CharacterDetailRoute
import com.mpapps.marvelcompose.ui.views.charactersList.CharactersListDestinations
import com.mpapps.marvelcompose.ui.views.charactersList.CharactersListScreenRoute

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = CharactersListScreenRoute.getUriRouteData()
    ) {
        composable(CharactersListScreenRoute.getUriRouteData()) {
            CharactersListDestinations(navController)
        }
        composable(
            CharacterDetailRoute.getUriRouteData(),
            enterTransition = { enterTransitionScaleIn() },
            exitTransition = { exitTransitionScaleIn() }
        ) { backStackEntry ->
            val character = backStackEntry.arguments?.getString(CharacterDetailRoute.PARAMETER)
            checkNotNull(character)
            CharacterDetailDestination(navController, character)
        }
    }
}

fun enterTransitionScaleIn() =
    scaleIn(
        animationSpec = tween(
            1000, easing = LinearEasing
        )
    ) + fadeIn(
        animationSpec = tween(900, easing = EaseIn),
    )

fun exitTransitionScaleIn() =
    scaleOut(
        animationSpec = tween(
            1000, easing = LinearEasing
        )
    ) + fadeOut(
        animationSpec = tween(900, easing = EaseIn),
    )
