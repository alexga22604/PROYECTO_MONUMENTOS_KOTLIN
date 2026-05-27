package com.example.monumentos_practica6.ui.monument_map.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private const val DEFAULT_ZOOM = 5f
private const val DEFAULT_LAT = 40.416775
private const val DEFAULT_LNG = -3.703790
private const val LOGO_URL = "https://cdn-icons-png.flaticon.com/512/73/73927.png"
private const val EMPTY_STRING = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonumentMapScreen(
    viewModel: MonumentViewModel,
    paddingValues: PaddingValues,
    onMenuClick: () -> Unit,
    onNavigateToList: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToAdd: (Double, Double) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(DEFAULT_LAT, DEFAULT_LNG), DEFAULT_ZOOM)
    }

    val mapProperties = remember {
        MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_light)
        )
    }

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.map)) },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.menu_description)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToList) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = stringResource(R.string.action_list_view)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapLongClick = { latLng ->
                onNavigateToAdd(latLng.latitude, latLng.longitude)
            }
        ) {
            state.filteredMonuments.forEach { monument ->
                MarkerInfoWindowContent(
                    state = MarkerState(position = LatLng(monument.latitude, monument.longitude)),
                    title = monument.name,
                    snippet = monument.city,
                    onInfoWindowClick = {
                        onNavigateToDetail(monument.id)
                    }
                ) { marker ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = LOGO_URL,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(text = marker.title ?: EMPTY_STRING)
                        Text(text = marker.snippet ?: EMPTY_STRING)
                    }
                }
            }

            state.myMonuments.forEach { customMonument ->
                MarkerInfoWindowContent(
                    state = MarkerState(position = LatLng(customMonument.latitude, customMonument.longitude)),
                    title = customMonument.name,
                    snippet = customMonument.city,
                    onInfoWindowClick = {
                        onNavigateToDetail(customMonument.id)
                    }
                ) { marker ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = LOGO_URL,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(text = marker.title ?: EMPTY_STRING)
                        Text(text = marker.snippet ?: EMPTY_STRING)
                    }
                }
            }
        }
    }
}