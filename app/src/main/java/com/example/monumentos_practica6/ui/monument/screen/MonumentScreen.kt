package com.example.monumentos_practica6.ui.monument.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument.component.FilterDialog
import com.example.monumentos_practica6.ui.monument.component.MonumentItem
import com.example.monumentos_practica6.ui.monument.component.MonumentTopBar
import com.example.monumentos_practica6.ui.monument.component.SortDialog
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel
import com.example.monumentos_practica6.ui.theme.FabBackgroundBlueGrey
import com.example.monumentos_practica6.ui.theme.FabContentColor

@Composable
fun MonumentScreen(
    viewModel: MonumentViewModel = viewModel(),
    paddingValues: PaddingValues,
    onMenuClick: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToMap: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var showSortDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        topBar = {
            MonumentTopBar(
                onMenuClick = onMenuClick,
                onSortClick = { showSortDialog = true },
                onFilterClick = { showFilterDialog = true }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToMap,
                containerColor = FabBackgroundBlueGrey,
                contentColor = FabContentColor,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Navigation,
                    contentDescription = stringResource(id = R.string.map_view_description)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(state.filteredMonuments) { monument ->
                MonumentItem(
                    monument = monument,
                    onClick = { onNavigateToDetail(monument.id) }
                )
            }
        }
    }

    if (showSortDialog) {
        SortDialog(
            currentSort = state.sortType,
            onSortSelected = {
                viewModel.setSortType(it)
                showSortDialog = false
            },
            onDismiss = { showSortDialog = false }
        )
    }

    if (showFilterDialog) {
        FilterDialog(
            countries = state.availableCountries,
            selectedCountry = state.selectedCountry,
            onCountrySelected = {
                viewModel.setCountryFilter(it)
                showFilterDialog = false
            },
            onDismiss = { showFilterDialog = false }
        )
    }
}