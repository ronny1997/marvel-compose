package com.mpapps.marvelcompose.domain.model

import android.graphics.Bitmap
import androidx.palette.graphics.Palette
import java.io.Serializable

data class Characters(
    val id: String,
    val name: String,
    val description: String,
    val bitmapThumbnail: Bitmap?,
) : Serializable {
    var color: Int? = null

    init {
        loadColor()
    }

    private fun loadColor() {
        bitmapThumbnail?.let {
            val palette = Palette.from(bitmapThumbnail).generate()
            val dominantSwatch = palette.dominantSwatch
             color = dominantSwatch?.rgb
        }
    }
}