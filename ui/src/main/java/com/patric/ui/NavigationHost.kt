package com.patric.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patric.core.utils.Navigator
import com.patric.ui.like.LikeView
import com.patric.ui.login.LoginView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavigationHost(
    navController: NavHostController,
    navigator: Navigator
) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach { navAction ->
            when (val nav = navAction?.navTarget) {
                is Navigator.NavTarget.OnBackPressed -> navController.navigateUp()
                is Navigator.NavTarget.PopUpTo ->  navController.navigate(nav.route){popUpTo(nav.popUpTo){ inclusive = navAction.inclusive} }
                else -> navAction?.navTarget?.route?.let { navController.navigate(it) }
            }
        }.launchIn(this)
    }
    NavHost(
        navController = navController,
        startDestination = Navigator.NavTarget.Login.route
    ) {
        composable(Navigator.NavTarget.Login.route) {
            LoginView()
        }
        composable(Navigator.NavTarget.Like.route) {
            LikeView()
        }
    }
}