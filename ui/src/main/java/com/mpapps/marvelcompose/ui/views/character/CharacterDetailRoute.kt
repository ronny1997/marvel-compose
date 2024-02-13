package com.mpapps.marvelcompose.ui.views.character

import android.net.Uri
import com.google.gson.Gson
import com.mpapps.marvelcompose.ui.navigation.Route
import com.mpapps.marvelcompose.ui.views.charactersList.model.CharactersUi

internal object CharacterDetailRoute : Route {

    const val PARAMETER = "CHARACTERS_DETAIL"
    private const val URI = "CHARACTERS_DETAIL/{$PARAMETER}"
    override fun getUriRouteData(): String = URI

    fun getUriRouteDataWhitArguments(charactersUi: CharactersUi): String {
        val characterJsonEncode = Uri.encode(Gson().toJson(charactersUi))
        return URI.replace(
            oldValue = "{${PARAMETER}}",
            newValue = characterJsonEncode,
        )
    }
}