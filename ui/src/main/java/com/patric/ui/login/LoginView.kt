package com.patric.ui.login

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginView(
    viewModel: LoginViewModel = hiltViewModel()
) {
LoginForm()
}

@Composable
fun LoginForm() {
    Text(text = "Test")
}


fun sss() {

}

@Preview
@Composable
fun LoginPreview() {
    LoginForm()
}