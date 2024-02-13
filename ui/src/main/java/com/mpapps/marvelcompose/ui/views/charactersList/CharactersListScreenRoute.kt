package com.mpapps.marvelcompose.ui.views.charactersList

import com.mpapps.marvelcompose.ui.navigation.Route

internal object CharactersListScreenRoute : Route {

    private const val URI = "CHARACTERS"
    override fun getUriRouteData(): String = URI
}