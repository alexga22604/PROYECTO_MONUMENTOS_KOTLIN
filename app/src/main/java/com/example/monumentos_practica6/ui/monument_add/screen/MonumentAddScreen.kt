package com.example.monumentos_practica6.ui.monument_add.screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument_add.viewmodel.MonumentAddViewModel
import com.example.monumentos_practica6.ui.theme.MapAddBoxGray
import com.example.monumentos_practica6.ui.theme.MapFabDarkBlue
import com.example.monumentos_practica6.ui.theme.MapFabWhite
import com.example.monumentos_practica6.ui.theme.MapFormDivider
import com.example.monumentos_practica6.ui.theme.MapFormLabel
import com.example.monumentos_practica6.ui.theme.MapMarkerOrange
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import java.io.File

private const val MAP_HEIGHT = 200
private const val MAP_ZOOM = 15f
private const val DEFAULT_ZOOM = 15f
private const val DEFAULT_LAT = 40.4168
private const val DEFAULT_LNG = -3.7038
private const val IMAGE_BOX_SIZE = 100
private const val CHAR_LIMIT = 250
private const val EMPTY_STRING = ""
private const val CHAR_LIMIT_DIVIDER = "/"
private const val DELETE_ICON_ALPHA = 0.5f
private const val BITMAP_QUALITY = 100
private const val TEMP_IMAGE_PREFIX = "image_"
private const val TEMP_IMAGE_SUFFIX = ".jpg"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MonumentAddScreen(
    latitude: Double,
    longitude: Double,
    viewModel: MonumentAddViewModel,
    paddingValues: PaddingValues,
    onMonumentSaved: (String, String, String, String, Double, Double, List<String>) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var showImageDialog by remember { mutableStateOf(false) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            val permanentUris = uris.mapNotNull { copyUriToInternalStorage(context, it) }
            viewModel.addImages(permanentUris)
        }
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            bitmap?.let {
                val uri = saveBitmapToInternalStorage(context, it)
                viewModel.addImages(listOf(uri))
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.initializeLocation(latitude, longitude)
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            Toast.makeText(context, R.string.monument_added_successfully, Toast.LENGTH_SHORT).show()
            onMonumentSaved(
                state.name,
                state.description,
                state.country,
                state.city,
                state.latitude,
                state.longitude,
                state.images.map { it.toString() }
            )
        }
    }
    val isFormValid = state.name.isNotBlank() &&
            state.description.isNotBlank() &&
            state.country.isNotBlank() &&
            state.city.isNotBlank() &&
            state.images.isNotEmpty() &&
            (state.latitude != 0.0 && state.longitude != 0.0)

    if (showImageDialog) {
        AlertDialog(
            onDismissRequest = { showImageDialog = false },
            title = { Text(text = stringResource(id = R.string.image_source_title)) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.camera),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageDialog = false
                                cameraLauncher.launch(null)
                            }
                            .padding(8.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.gallery),
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showImageDialog = false
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                            .padding(8.dp)
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showImageDialog = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            FormTextField(
                label = stringResource(id = R.string.monument_name_label),
                value = state.name,
                onValueChange = { viewModel.updateName(it) }
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                FormTextField(
                    label = stringResource(id = R.string.monument_description_label),
                    value = state.description,
                    onValueChange = { viewModel.updateDescription(it) }
                )
                Text(
                    text = "${state.description.length}$CHAR_LIMIT_DIVIDER$CHAR_LIMIT",
                    fontSize = 12.sp,
                    color = MapFormLabel,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            FormTextField(
                label = stringResource(id = R.string.monument_country_label),
                value = state.country,
                onValueChange = { viewModel.updateCountry(it) }
            )

            FormTextField(
                label = stringResource(id = R.string.monument_city_label),
                value = state.city,
                onValueChange = { viewModel.updateCity(it) }
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(id = R.string.monument_location_label),
                    color = MapFormLabel,
                    fontSize = 14.sp
                )
                val initialLat = if (state.latitude != 0.0) state.latitude else DEFAULT_LAT
                val initialLng = if (state.longitude != 0.0) state.longitude else DEFAULT_LNG
                val initialZoom = if (state.latitude != 0.0) MAP_ZOOM else DEFAULT_ZOOM
                val cameraPositionState = rememberCameraPositionState {
                    position =
                        CameraPosition.fromLatLngZoom(LatLng(initialLat, initialLng), initialZoom)
                }

                LaunchedEffect(state.latitude, state.longitude) {
                    if (state.latitude != 0.0 && state.longitude != 0.0) {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLng(
                                LatLng(
                                    state.latitude,
                                    state.longitude
                                )
                            )
                        )
                    }
                }

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MAP_HEIGHT.dp),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                            context,
                            R.raw.map_style_light
                        )
                    ),
                    uiSettings = MapUiSettings(zoomControlsEnabled = false, compassEnabled = false),
                    onMapLongClick = { latLng ->
                        viewModel.updateLocation(
                            latLng.latitude,
                            latLng.longitude
                        )
                    }
                ) {
                    if (state.latitude != 0.0 && state.longitude != 0.0) {
                        MarkerComposable(
                            state = rememberMarkerState(
                                position = LatLng(
                                    state.latitude,
                                    state.longitude
                                )
                            )
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

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(id = R.string.monument_images_label),
                    color = MapFormLabel,
                    fontSize = 14.sp
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(state.images) { uri ->
                        Box(
                            modifier = Modifier
                                .size(IMAGE_BOX_SIZE.dp)
                                .combinedClickable(
                                    onClick = {},
                                    onLongClick = { viewModel.removeImage(uri) }
                                )
                        ) {
                            AsyncImage(
                                model = uri,
                                contentDescription = EMPTY_STRING,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(4.dp)
                                    .background(
                                        Color.Black.copy(alpha = DELETE_ICON_ALPHA),
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = EMPTY_STRING,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .size(IMAGE_BOX_SIZE.dp)
                                .background(MapAddBoxGray)
                                .clickable { showImageDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = EMPTY_STRING,
                                tint = Color.White,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { viewModel.saveToMyMonuments() },
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MapFabDarkBlue,
                    contentColor = MapFabWhite,
                    disabledContainerColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun FormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            color = MapFormLabel,
            fontSize = 14.sp
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MapFormDivider)
        )
    }
}

private fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): Uri {
    val file =
        File(context.filesDir, "$TEMP_IMAGE_PREFIX${System.currentTimeMillis()}$TEMP_IMAGE_SUFFIX")
    file.outputStream().use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUALITY, out)
        out.flush()
    }
    return Uri.fromFile(file)
}

private fun copyUriToInternalStorage(context: Context, uri: Uri): Uri? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(
            context.filesDir,
            "$TEMP_IMAGE_PREFIX${System.currentTimeMillis()}$TEMP_IMAGE_SUFFIX"
        )
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        Uri.fromFile(file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}