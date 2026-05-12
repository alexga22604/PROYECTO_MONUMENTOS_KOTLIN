package com.example.monumentos_practica6.ui

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.monumentos_practica6.ui.common.AppScaffold
import com.example.monumentos_practica6.ui.navigation.AppNavigation
import com.example.monumentos_practica6.ui.navigation.ScreenNav

@Composable
fun MonumentApp() {
    val backStack = rememberNavBackStack(ScreenNav.Launcher)
    val currentKey = backStack.lastOrNull()
    val showScaffoldComponents = currentKey !is ScreenNav.Launcher

    AppScaffold(
        showNavigationElements = showScaffoldComponents,
        onNavigate = { screen ->
            when (screen) {
                ScreenNav.Monuments -> {
                    backStack.clear()
                    backStack.add(ScreenNav.Monuments)
                }

                else -> {}
            }
        }
    ) { innerPadding ->
        AppNavigation(
            paddingValues = innerPadding,
            backStack = backStack
        )
    }
}