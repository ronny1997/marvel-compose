package com.mpapps.marvelcompose.ui.views.questions

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mpapps.marvelcompose.ui.views.players.viewmodel.CharactersViewModel
import com.mpapps.marvelcompose.ui.views.questions.viewmodel.CharactersDetailViewModel

@Composable
fun QuestionsScreen() {
    val viewModel: CharactersDetailViewModel = viewModel()
    Text(text = "QuestionsScreen")
}