package com.example.monumentos_practica6.ui.monument_map.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel
import com.example.monumentos_practica6.ui.monument_map.component.MonumentMapTopBar
import com.example.monumentos_practica6.ui.theme.MapFabDarkBlue
import com.example.monumentos_practica6.ui.theme.MapFabWhite
import com.example.monumentos_practica6.ui.theme.MapInfoWindowBackground
import com.example.monumentos_practica6.ui.theme.MapInfoWindowContent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

private const val MY_LOCATION_LAT = 40.4168
private const val MY_LOCATION_LNG = -3.7038
private const val MY_LOCATION_ZOOM = 15f
private const val DEFAULT_ZOOM_PADDING = 100

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
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()
    var isMapLoaded by remember { mutableStateOf(false) }
    var selectedMonument by remember { mutableStateOf<Int?>(null) }
    var isCenteredOnMe by remember { mutableStateOf(false) }

    val allMapMonuments = state.monuments + state.myMonuments

    LaunchedEffect(isMapLoaded, allMapMonuments) {
        if (isMapLoaded && allMapMonuments.isNotEmpty() && state.newlyAddedMonumentId == null) {
            val builder = LatLngBounds.builder()
            allMapMonuments.forEach { builder.include(LatLng(it.latitude, it.longitude)) }
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(
                    builder.build(),
                    DEFAULT_ZOOM_PADDING
                )
            )
        }
    }

    LaunchedEffect(isMapLoaded, state.newlyAddedMonumentId) {
        if (isMapLoaded && state.newlyAddedMonumentId != null) {
            val newMonument = allMapMonuments.find { it.id == state.newlyAddedMonumentId }
            if (newMonument != null) {
                selectedMonument = newMonument.id
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(newMonument.latitude, newMonument.longitude),
                        MY_LOCATION_ZOOM
                    )
                )
                viewModel.clearNewlyAddedMonument()
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        topBar = { MonumentMapTopBar(onMenuClick = onMenuClick) },
        floatingActionButton = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            if (isCenteredOnMe) {
                                if (allMapMonuments.isNotEmpty()) {
                                    val builder = LatLngBounds.builder()
                                    allMapMonuments.forEach {
                                        builder.include(LatLng(it.latitude, it.longitude))
                                    }
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLngBounds(
                                            builder.build(),
                                            DEFAULT_ZOOM_PADDING
                                        )
                                    )
                                }
                                isCenteredOnMe = false
                            } else {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(MY_LOCATION_LAT, MY_LOCATION_LNG),
                                        MY_LOCATION_ZOOM
                                    )
                                )
                                isCenteredOnMe = true
                            }
                        }
                    },
                    containerColor = MapFabWhite
                ) {
                    Icon(Icons.Default.MyLocation, null, tint = MapFabDarkBlue)
                }

                FloatingActionButton(
                    onClick = onNavigateToList,
                    containerColor = MapFabDarkBlue
                ) {
                    Icon(Icons.Default.List, null, tint = MapFabWhite)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLoaded = { isMapLoaded = true },
                onMapClick = { selectedMonument = null },
                onMapLongClick = { latLng -> onNavigateToAdd(latLng.latitude, latLng.longitude) }
            ) {
                allMapMonuments.forEach { monument ->
                    val markerState = rememberMarkerState(
                        key = monument.id.toString(),
                        position = LatLng(monument.latitude, monument.longitude)
                    )

                    MarkerComposable(
                        keys = arrayOf(monument.id, selectedMonument == monument.id),
                        state = markerState,
                        anchor = Offset(0.5f, 1f),
                        onClick = {
                            if (selectedMonument == monument.id) {
                                onNavigateToDetail(monument.id)
                            } else {
                                selectedMonument = monument.id
                                coroutineScope.launch {
                                    cameraPositionState.animate(
                                        CameraUpdateFactory.newLatLng(markerState.position)
                                    )
                                }
                            }
                            true
                        }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (selectedMonument == monument.id) {
                                Surface(
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(100.dp),
                                    color = MapInfoWindowBackground,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Box(modifier = Modifier.padding(12.dp)) {
                                        Column {
                                            Text(
                                                monument.name,
                                                color = MapInfoWindowContent,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                monument.country,
                                                color = MapInfoWindowContent,
                                                fontSize = 12.sp
                                            )
                                        }
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,
                                            tint = MapInfoWindowContent,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .align(Alignment.BottomEnd)
                                        )
                                    }
                                }
                                Canvas(modifier = Modifier.size(24.dp, 16.dp)) {
                                    drawPath(
                                        path = Path().apply {
                                            moveTo(0f, 0f)
                                            lineTo(size.width, 0f)
                                            lineTo(size.width / 2f, size.height)
                                            close()
                                        },
                                        color = MapInfoWindowBackground
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = null,
                                tint = MapFabDarkBlue,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}