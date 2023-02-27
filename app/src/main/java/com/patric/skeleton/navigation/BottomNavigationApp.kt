package com.patric.skeleton.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.patric.core.Screen
import com.patric.core.utils.NavAction
import com.patric.core.utils.Navigator

@Composable
fun BottomNavigationApp(
    navController: Navigator,
    navHostController: NavHostController
) {
    val items = listOf(
        Screen.Login,
        Screen.Like
    )
    BottomNavigation(
        backgroundColor = Color.Red,
        elevation = 10.dp,
        modifier = Modifier
            .height(50.dp)
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(10.dp))

    ) {
        val backstackEntry by navHostController.currentBackStackEntryAsState()
        val currentDestination = backstackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.navTarget.route) },
                alwaysShowLabel = true,
                selectedContentColor = Color.White,
                selected = currentDestination?.hierarchy?.any { screen.navTarget.route == it.route } == true,
                onClick = { navController.navigateTo(NavAction(screen.navTarget)) }
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationApp(Navigator(), rememberNavController())
}