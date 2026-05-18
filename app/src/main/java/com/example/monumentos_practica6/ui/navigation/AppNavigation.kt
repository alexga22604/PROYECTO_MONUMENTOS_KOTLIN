package com.example.monumentos_practica6.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.monumentos_practica6.data.repository.MonumentRepository
import com.example.monumentos_practica6.ui.launcher.screen.LauncherScreen
import com.example.monumentos_practica6.ui.monument.screen.MonumentScreen
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel
import com.example.monumentos_practica6.ui.monument_detail.screen.MonumentDetailScreen
import com.example.monumentos_practica6.ui.monument_detail.viewmodel.MonumentDetailViewModel
import com.example.monumentos_practica6.ui.monument_detail.viewmodel.MonumentDetailViewModelFactory

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
                    onNavigateToDetail = { monumentId ->
                        backStack.add(ScreenNav.MonumentDetail(monumentId))
                    },
                    onNavigateToMap = {
                        backStack.add(ScreenNav.Map)
                    }
                )
            }

            entry<ScreenNav.MonumentDetail> { screen ->
                val repository = remember { MonumentRepository() }
                val factory = remember { MonumentDetailViewModelFactory(repository, screen.id) }
                val detailViewModel: MonumentDetailViewModel = viewModel(
                    key = screen.id.toString(),
                    factory = factory
                )

                MonumentDetailScreen(
                    viewModel = detailViewModel,
                    onBack = { backStack.removeLastOrNull() }
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