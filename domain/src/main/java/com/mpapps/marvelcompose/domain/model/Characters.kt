package com.mpapps.marvelcompose.domain.model

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import java.io.Serializable

data class Characters(
    val id: String,
    val name: String,
    val description: String,
    val bitmapThumbnail: Bitmap?,
    val thumbnailUrl: String,
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