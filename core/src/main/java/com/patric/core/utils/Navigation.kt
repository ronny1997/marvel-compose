package com.patric.core.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Navigator {
    private val _sharedFlow = MutableStateFlow(NavAction(null))
    val sharedFlow: StateFlow<NavAction?>
    get() = _sharedFlow

    fun navigateTo(navAction: NavAction) {
        _sharedFlow.tryEmit(navAction)
    }

    sealed class NavTarget(val route: String, val popUpTo: String = "") {
        object Login : NavTarget("login")
        object Like : NavTarget("like")
        class Workflow(argument: String = "/{userId}") : NavTarget("workflow$argument") {
            companion object {
                const val ARGUMENT_NAME = "userId"
            }
        }
        object OnBackPressed : NavTarget("")

        class PopUpTo(goTo: String, popUpToRoute: String) : NavTarget(goTo, popUpToRoute)
    }
}

data class NavAction(
    val navTarget: Navigator.NavTarget?,
    val inclusive: Boolean = false
)
