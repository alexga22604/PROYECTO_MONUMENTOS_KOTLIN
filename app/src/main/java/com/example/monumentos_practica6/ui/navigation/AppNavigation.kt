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
import com.example.monumentos_practica6.ui.monument_add.screen.MonumentAddScreen
import com.example.monumentos_practica6.ui.monument_add.viewmodel.MonumentAddViewModel
import com.example.monumentos_practica6.ui.monument_detail.screen.MonumentDetailScreen
import com.example.monumentos_practica6.ui.monument_detail.viewmodel.MonumentDetailViewModel
import com.example.monumentos_practica6.ui.monument_detail.viewmodel.MonumentDetailViewModelFactory
import com.example.monumentos_practica6.ui.monument_map.screen.MonumentMapScreen
import com.example.monumentos_practica6.ui.my_monument.screen.MonumentDetailCustomScreen
import com.example.monumentos_practica6.ui.my_monument.screen.MyMonumentScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
private const val EMPTY_STRING = ""

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
                val state by viewModel.uiState.collectAsState()
                val customMonument = state.myMonuments.find { it.id == screen.id }

                if (customMonument != null) {
                    MonumentDetailCustomScreen(
                        monument = customMonument,
                        onBack = { backStack.removeLastOrNull() },
                        onDelete = {
                            viewModel.deleteCustomMonument(screen.id)
                            backStack.removeLastOrNull()
                        }
                    )
                } else {
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
            }

            entry<ScreenNav.Map> { _ ->
                MonumentMapScreen(
                    viewModel = viewModel,
                    paddingValues = PaddingValues(),
                    onMenuClick = onOpenDrawer,
                    onNavigateToList = {
                        backStack.removeLastOrNull()
                    },
                    onNavigateToDetail = { id ->
                        backStack.add(ScreenNav.MonumentDetail(id))
                    },
                    onNavigateToAdd = { lat, lng ->
                        backStack.add(ScreenNav.MonumentAdd(lat, lng))
                    }
                )
            }

            entry<ScreenNav.MonumentAdd> { screen ->
                val addViewModel: MonumentAddViewModel = viewModel()

                MonumentAddScreen(
                    latitude = screen.latitude,
                    longitude = screen.longitude,
                    viewModel = addViewModel,
                    paddingValues = PaddingValues(),
                    onMonumentSaved = { name, description, country, city, lat, lng, images ->
                        viewModel.addCustomMonument(
                            name = name,
                            description = description,
                            country = country,
                            city = city,
                            location = EMPTY_STRING,
                            latitude = lat,
                            longitude = lng,
                            images = images
                        )
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<ScreenNav.Favorites> { _ ->
            }

            entry<ScreenNav.MyMonuments> { _ ->
                MyMonumentScreen(
                    viewModel = viewModel,
                    paddingValues = PaddingValues(),
                    onMenuClick = onOpenDrawer,
                    onNavigateToAdd = { lat, lng ->
                        backStack.add(ScreenNav.MonumentAdd(lat, lng))
                    },
                    onNavigateToDetail = { id ->
                        backStack.add(ScreenNav.MonumentDetail(id))
                    }
                )
            }
        }
    )
}