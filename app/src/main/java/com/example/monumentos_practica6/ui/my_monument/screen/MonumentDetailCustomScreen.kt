package com.example.monumentos_practica6.ui.my_monument.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.data.domain.model.MonumentBo
import com.example.monumentos_practica6.ui.monument_detail.component.MonumentCarousel
import com.example.monumentos_practica6.ui.monument_detail.component.MonumentDetailTopBar
import com.example.monumentos_practica6.ui.theme.InfoIconColor
import com.example.monumentos_practica6.ui.theme.MapMarkerOrange
import com.example.monumentos_practica6.ui.theme.TextPrimaryDark
import com.example.monumentos_practica6.ui.theme.TextSecondaryDark
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

private const val EMPTY_STRING = ""

@Composable
fun MonumentDetailCustomScreen(
    monument: MonumentBo,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val showTopBarTitle by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    var showDialog by remember { mutableStateOf(false) }

    val shareSubject = stringResource(id = R.string.email_subject_format, monument.name)
    val shareBody = stringResource(
        id = R.string.email_body_format,
        monument.name,
        monument.city,
        monument.country,
        EMPTY_STRING
    )
    val mailtoScheme = stringResource(id = R.string.mailto_scheme)
    val geoUriString = stringResource(
        id = R.string.geo_uri_format,
        monument.latitude.toString(),
        monument.longitude.toString(),
        monument.name
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(id = R.string.delete_monument_title)) },
            text = { Text(stringResource(id = R.string.delete_monument_message)) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDelete()
                }) {
                    Text(stringResource(id = R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            MonumentDetailTopBar(
                title = monument.name,
                showTitle = showTopBarTitle,
                onBackClick = onBack,
                onMapClick = {
                    val uri = Uri.parse(geoUriString)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                },
                onShareClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse(mailtoScheme)
                        putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                        putExtra(Intent.EXTRA_TEXT, shareBody)
                    }
                    context.startActivity(intent)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                MonumentCarousel(images = monument.images)
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = monument.name,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark,
                            modifier = Modifier.weight(1f)
                        )

                        if (monument.wikiUrl.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(monument.wikiUrl)
                                    )
                                    context.startActivity(intent)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = stringResource(id = R.string.info_description),
                                    tint = InfoIconColor,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        IconButton(
                            onClick = { showDialog = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete),
                                tint = Color.Red,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Text(
                        text = stringResource(
                            id = R.string.city_location_format,
                            monument.city,
                            monument.country
                        ),
                        fontSize = 18.sp,
                        color = TextSecondaryDark,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = monument.description,
                        fontSize = 16.sp,
                        color = TextPrimaryDark,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    val monumentLatLng = LatLng(monument.latitude, monument.longitude)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(monumentLatLng, 15f)
                    }

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(
                            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                                context,
                                R.raw.map_style_light
                            )
                        ),
                        uiSettings = MapUiSettings(
                            zoomControlsEnabled = false,
                            scrollGesturesEnabled = false,
                            rotationGesturesEnabled = false,
                            tiltGesturesEnabled = false,
                            compassEnabled = false
                        )
                    ) {
                        MarkerComposable(
                            state = rememberMarkerState(position = monumentLatLng)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Navigation,
                                contentDescription = EMPTY_STRING,
                                tint = MapMarkerOrange,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}