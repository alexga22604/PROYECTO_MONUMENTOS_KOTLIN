package com.example.monumentos_practica6.ui.monument_detail.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonumentDetailTopBar(
    title: String,
    showTitle: Boolean,
    onBackClick: () -> Unit,
    onMapClick: () -> Unit,
    onShareClick: () -> Unit
) {
    TopAppBar(
        title = { if (showTitle) Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null
                )
            }
            IconButton(onClick = onMapClick) {
                Icon(
                    imageVector = Icons.Filled.Map,
                    contentDescription = null
                )
            }
        }
    )
}