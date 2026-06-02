package com.example.monumentos_practica6.ui.monument_detail.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument_detail.viewmodel.MonumentDetailViewModel

private const val EMPTY_STRING = ""
private const val GMAIL_PACKAGE = "com.google.android.gm"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonumentDetailScreen(
    viewModel: MonumentDetailViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val monument = state.monument
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    var currentImageIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = monument?.name ?: EMPTY_STRING) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back)
                        )
                    }
                },
                actions = {
                    if (monument != null) {
                        val subject = stringResource(R.string.email_subject_format, monument.name)
                        val body = stringResource(
                            R.string.email_body_format,
                            monument.name,
                            monument.city,
                            monument.country,
                            monument.wikiUrl
                        )
                        val mailto = stringResource(R.string.mailto_scheme)

                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse(mailto)
                                setPackage(GMAIL_PACKAGE)
                                putExtra(Intent.EXTRA_SUBJECT, subject)
                                putExtra(Intent.EXTRA_TEXT, body)
                            }
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                context.startActivity(Intent.createChooser(intent, EMPTY_STRING))
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = stringResource(R.string.action_share)
                            )
                        }
                        val geoUri = stringResource(
                            R.string.geo_uri_format,
                            monument.latitude.toString(),
                            monument.longitude.toString(),
                            monument.name
                        )

                        IconButton(onClick = {
                            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
                            context.startActivity(mapIntent)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Map,
                                contentDescription = stringResource(R.string.action_open_map)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else if (monument != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                if (monument.images.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        AsyncImage(
                            model = monument.images[currentImageIndex],
                            contentDescription = EMPTY_STRING,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { if (currentImageIndex > 0) currentImageIndex-- },
                                enabled = currentImageIndex > 0
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.previous_image),
                                    tint = Color.White
                                )
                            }

                            IconButton(
                                onClick = { if (currentImageIndex < monument.images.size - 1) currentImageIndex++ },
                                enabled = currentImageIndex < monument.images.size - 1
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = stringResource(R.string.next_image),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = monument.name,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            if (monument.wikiUrl.isNotEmpty()) {
                                uriHandler.openUri(monument.wikiUrl)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(R.string.info_description),
                                tint = Color.Blue,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        IconButton(onClick = { viewModel.toggleFavorite() }) {
                            Icon(
                                imageVector = if (monument.isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                                contentDescription = stringResource(R.string.favorite_toggle_description),
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Text(
                        text = stringResource(
                            R.string.city_location_format,
                            monument.city,
                            monument.country
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = monument.description,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    val staticMapUrl = stringResource(
                        R.string.static_map_url_format,
                        monument.longitude.toString(),
                        monument.latitude.toString()
                    )

                    AsyncImage(
                        model = staticMapUrl,
                        contentDescription = stringResource(R.string.static_map_description),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}