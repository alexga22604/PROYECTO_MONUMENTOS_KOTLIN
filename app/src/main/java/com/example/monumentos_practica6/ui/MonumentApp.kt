package com.example.monumentos_practica6.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.monumentos_practica6.ui.common.AppScaffold
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel
import com.example.monumentos_practica6.ui.navigation.AppNavigation
import com.example.monumentos_practica6.ui.navigation.ScreenNav

@Composable
fun MonumentApp() {
    val viewModel: MonumentViewModel = hiltViewModel()

    val backStack = rememberNavBackStack(ScreenNav.Launcher)
    val currentKey = backStack.lastOrNull()
    val showScaffoldComponents = currentKey !is ScreenNav.Launcher

    AppScaffold(
        showNavigationElements = showScaffoldComponents,
        currentRoute = currentKey as? ScreenNav,
        onNavigate = { screen ->
            val oldScreens = backStack.toList()
            when (screen) {
                is ScreenNav.Monuments -> {
                    backStack.add(ScreenNav.Monuments)
                    oldScreens.forEach { backStack.remove(it) }
                }

                is ScreenNav.Favorites -> {
                    backStack.add(ScreenNav.Favorites)
                    oldScreens.forEach { backStack.remove(it) }
                }

                is ScreenNav.MyMonuments -> {
                    backStack.add(ScreenNav.MyMonuments)
                    oldScreens.forEach { backStack.remove(it) }
                }

                else -> {}
            }
        }
    ) { innerPadding, openDrawer ->
        AppNavigation(
            paddingValues = innerPadding,
            backStack = backStack,
            viewModel = viewModel,
            onOpenDrawer = openDrawer
        )
    }
}