package com.example.monumentos_practica6.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.monumentos_practica6.data.local.MyMonumentPreferences
import com.example.monumentos_practica6.data.repository.MonumentRepository
import com.example.monumentos_practica6.ui.common.AppScaffold
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModelFactory
import com.example.monumentos_practica6.ui.navigation.AppNavigation
import com.example.monumentos_practica6.ui.navigation.ScreenNav

@Composable
fun MonumentApp() {
    val context = LocalContext.current
    val repository = remember { MonumentRepository() }
    val preferences = remember { MyMonumentPreferences(context) }
    val factory = remember { MonumentViewModelFactory(repository, preferences) }
    val viewModel: MonumentViewModel = viewModel(factory = factory)

    val backStack = rememberNavBackStack(ScreenNav.Launcher)
    val currentKey = backStack.lastOrNull()
    val showScaffoldComponents = currentKey !is ScreenNav.Launcher

    AppScaffold(
        showNavigationElements = showScaffoldComponents,
        currentRoute = currentKey as? ScreenNav,
        onNavigate = { screen ->
            when (screen) {
                is ScreenNav.Monuments -> {
                    backStack.clear()
                    backStack.add(ScreenNav.Monuments)
                }

                is ScreenNav.Favorites -> {
                    backStack.clear()
                    backStack.add(ScreenNav.Favorites)
                }

                is ScreenNav.MyMonuments -> {
                    backStack.clear()
                    backStack.add(ScreenNav.MyMonuments)
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