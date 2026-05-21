package com.example.monumentos_practica6.ui.monument_detail.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument_detail.component.MonumentCarousel
import com.example.monumentos_practica6.ui.monument_detail.component.MonumentDetailTopBar
import com.example.monumentos_practica6.ui.monument_detail.viewmodel.MonumentDetailViewModel
import com.example.monumentos_practica6.ui.theme.FavoriteStarActive
import com.example.monumentos_practica6.ui.theme.FavoriteStarInactive
import com.example.monumentos_practica6.ui.theme.InfoIconColor
import com.example.monumentos_practica6.ui.theme.TextPrimaryDark
import com.example.monumentos_practica6.ui.theme.TextSecondaryDark

private const val EMPTY_STRING = ""

@Composable
fun MonumentDetailScreen(
    viewModel: MonumentDetailViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val showTopBarTitle by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val monument = state.monument
    val shareSubject = monument?.let { stringResource(id = R.string.email_subject_format, it.name) }
        ?: EMPTY_STRING
    val shareBody = monument?.let {
        stringResource(
            id = R.string.email_body_format,
            it.name,
            it.city,
            it.country,
            it.wikiUrl
        )
    } ?: EMPTY_STRING
    val mailtoScheme = stringResource(id = R.string.mailto_scheme)
    val geoUriString = monument?.let {
        stringResource(
            id = R.string.geo_uri_format,
            it.latitude.toString(),
            it.longitude.toString(),
            it.name
        )
    }

    Scaffold(
        topBar = {
            MonumentDetailTopBar(
                title = monument?.name ?: EMPTY_STRING,
                showTitle = showTopBarTitle,
                onBackClick = onBack,
                onMapClick = {
                    geoUriString?.let { uriString ->
                        val uri = Uri.parse(uriString)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    }
                },
                onShareClick = {
                    if (monument != null) {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse(mailtoScheme)
                            putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                            putExtra(Intent.EXTRA_TEXT, shareBody)
                        }
                        context.startActivity(intent)
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            monument?.let { currentMonument ->
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        MonumentCarousel(images = currentMonument.images)
                    }

                    item {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = currentMonument.name,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryDark,
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(
                                    onClick = {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(currentMonument.wikiUrl)
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

                                IconButton(
                                    onClick = { viewModel.toggleFavorite() }
                                ) {
                                    Icon(
                                        imageVector = if (currentMonument.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                        contentDescription = stringResource(id = R.string.favorite_toggle_description),
                                        tint = if (currentMonument.isFavorite) FavoriteStarActive else FavoriteStarInactive,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }

                            Text(
                                text = stringResource(
                                    id = R.string.city_location_format,
                                    currentMonument.city,
                                    currentMonument.country
                                ),
                                fontSize = 18.sp,
                                color = TextSecondaryDark,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Text(
                                text = currentMonument.description,
                                fontSize = 16.sp,
                                color = TextPrimaryDark,
                                lineHeight = 24.sp
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                            val mapUrl = stringResource(
                                id = R.string.static_map_url_format,
                                currentMonument.longitude.toString(),
                                currentMonument.latitude.toString()
                            )

                            AsyncImage(
                                model = mapUrl,
                                contentDescription = stringResource(id = R.string.static_map_description),
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
    }
}