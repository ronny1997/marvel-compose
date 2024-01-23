package com.mpapps.marvelcompose.ui.views.players.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import java.io.Serializable

data class CharactersUi(
    val id: String,
    val name: String,
    val description: String,
    val thumbnail: String,
    val color: ULong? = null,
) : Serializable