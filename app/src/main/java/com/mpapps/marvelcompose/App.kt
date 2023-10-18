package com.mpapps.marvelcompose

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.mpapps.marvelcompose.ui.NavigationHost
import com.mpapps.marvelcompose.ui.theme.AppTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App() {
    val navController = rememberNavController()
    AppTheme {
        Scaffold(
            drawerShape = MaterialTheme.shapes.medium
        ){
            NavigationHost(navController)
        }
    }
}