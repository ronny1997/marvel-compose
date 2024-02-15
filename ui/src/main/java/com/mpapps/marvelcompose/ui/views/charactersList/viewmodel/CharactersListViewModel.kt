package com.mpapps.marvelcompose.ui.views.charactersList.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.Size
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.usecase.GetCharactersUseCase
import com.mpapps.marvelcompose.ui.infrastructure.BaseViewModel
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListEffect
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListEvent
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
internal class CharactersListViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
    @ApplicationContext val appContext: Context
) : BaseViewModel<CharactersListEffect, CharactersListEvent, CharactersListViewState>() {

    init {
        if (uiState.data.isEmpty()) {
            getCharacters()
        }
    }

    override fun onEvent(event: CharactersListEvent) {
        when (event) {
            is CharactersListEvent.GetCharacters -> getCharacters()
            is CharactersListEvent.NavigationToDetail -> goToDetail(event.charactersUi)
        }
    }

    private fun goToDetail(charactersUi: Characters) {
        setEffect {
            CharactersListEffect.Navigation.NavigateToDetail(charactersUi)
        }
    }

    override fun setInitialState(): CharactersListViewState {
        return CharactersListViewState()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            if (!uiState.isLoading) {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                charactersUseCase()
                    .collectLatest { result ->
                        result.fold(::handleError) { newData ->
                            val data = uiState.data.apply {
                                putAll(newData.associateBy { it.id })
                            }
                            data.forEach {
                                val newCharacter = requestImage(it.value, it.value.thumbnailUrl)
                                data.replace(newCharacter.id, newCharacter)
                            }
                            setState {
                                copy(
                                    data = data,
                                    isLoading = false
                                )
                            }
                        }
                    }
            }
        }
    }

    private suspend fun requestImage(
        characters: Characters,
        thumbnailUrl: String,
    ): Characters {
        return suspendCoroutine { continuation ->
            val imageRequest = ImageRequest.Builder(appContext)
                .data(thumbnailUrl)
                .size(Size.ORIGINAL)
                .listener { _, result ->
                    loadImage(characters, result.drawable) {
                        continuation.resume((it))
                    }
                }
                .build()
            ImageLoader(appContext).enqueue(imageRequest)
        }
    }

    private fun loadImage(
        characters: Characters,
        drawable: Drawable,
        onLoadImage: (Characters) -> Unit
    ) {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val convertBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val color = loadColor(convertBitmap)
        val newCharacter = characters.copy(bitmapThumbnail = convertBitmap, color = color)
        onLoadImage(newCharacter)
    }

    private fun loadColor(bitmapThumbnail: Bitmap): Int? {
        return bitmapThumbnail.let {
            val palette = Palette.from(bitmapThumbnail).generate()
            val dominantSwatch = palette.dominantSwatch
            dominantSwatch?.rgb
        }
    }
}