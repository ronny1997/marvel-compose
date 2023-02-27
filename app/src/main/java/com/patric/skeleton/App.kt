package com.patric.skeleton

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.patric.core.utils.ErrorApp
import com.patric.core.utils.Navigator
import com.patric.skeleton.navigation.BottomNavigationApp
import com.patric.ui.NavigationHost
import com.patric.ui.components.ErrorBanner
import com.patric.ui.components.TopBar
import com.patric.ui.theme.ColorBlue
import com.patric.ui.theme.AppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SkeletonApp(
    navigator: Navigator,
    error: ErrorApp,
    viewModel: AppViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    viewModel.viewState.collectAsState()
    AppTheme {
        Scaffold(
            backgroundColor = ColorBlue,
            topBar = { TopBar() },
            bottomBar = { BottomNavigationApp(navigator, navController)},
            drawerShape = MaterialTheme.shapes.medium,
        ) {
            NavigationHost(navController = navController, navigator)
        }
        ErrorBanner(errorApp = error)
    }
}