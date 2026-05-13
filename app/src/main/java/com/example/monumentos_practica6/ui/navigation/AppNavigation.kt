package com.example.monumentos_practica6.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.monumentos_practica6.ui.launcher.screen.LauncherScreen
import com.example.monumentos_practica6.ui.monument.screen.MonumentScreen
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel

@Composable
fun AppNavigation(
    paddingValues: PaddingValues,
    backStack: NavBackStack<NavKey>,
    viewModel: MonumentViewModel,
    onOpenDrawer: () -> Unit
) {
    BackHandler(enabled = backStack.size > 1) {
        backStack.removeLastOrNull()
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = Modifier.padding(paddingValues),
        entryProvider = entryProvider {
            entry<ScreenNav.Launcher> { _ ->
                LauncherScreen(
                    onTimeout = {
                        backStack.clear()
                        backStack.add(ScreenNav.Monuments)
                    }
                )
            }

            entry<ScreenNav.Monuments> { _ ->
                MonumentScreen(
                    viewModel = viewModel,
                    paddingValues = PaddingValues(),
                    onMenuClick = onOpenDrawer,
                    onNavigateToDetail = { },
                    onNavigateToMap = { }
                )
            }

            entry<ScreenNav.Map> { _ ->
            }
            entry<ScreenNav.Favorites> { _ ->
            }

            entry<ScreenNav.MyMonuments> { _ ->
            }
        }
    )
}