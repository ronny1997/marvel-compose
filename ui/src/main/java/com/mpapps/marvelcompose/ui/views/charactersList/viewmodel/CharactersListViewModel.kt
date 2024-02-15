package com.mpapps.marvelcompose.ui.views.charactersList.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.viewModelScope
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
            is CharactersListEvent.LoadImage -> loadImage(event.id, event.thumbnailUrl)
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
                        result.fold(::handleError) { data ->
                            setState {
                                copy(
                                    data = uiState.data + data,
                                    isLoading = false
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun loadImage(id: String, thumbnailUrl: String) {
        val imageRequest = ImageRequest.Builder(appContext)
            .data(thumbnailUrl)
            .size(Size.ORIGINAL)
            .listener { _, result ->
                runImage(id, result.drawable)
            }
            .build()
        ImageLoader(appContext).enqueue(imageRequest)
    }

    private fun runImage(id: String, drawable: Drawable) {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val convertBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val data = uiState.data.map {
            if (it.id == id) it.copy(bitmapThumbnail = convertBitmap) else it
        }
        setState {
            copy(
                data = data,
                isLoading = false
            )
        }
    }

}