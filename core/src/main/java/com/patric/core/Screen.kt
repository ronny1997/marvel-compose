package com.patric.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector
import com.patric.core.utils.Navigator


sealed class Screen( var icon: ImageVector, var title: String, val navTarget: Navigator.NavTarget, var current: Boolean) {
    object Login : Screen( Icons.Filled.VerifiedUser, "Login", Navigator.NavTarget.Login, true)
    object Like : Screen( Icons.Filled.Home, "Like", Navigator.NavTarget.Like, false)
}