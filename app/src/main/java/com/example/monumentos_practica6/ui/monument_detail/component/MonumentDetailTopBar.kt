package com.example.monumentos_practica6.ui.monument_detail.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.theme.TopBarBackgroundLight
import com.example.monumentos_practica6.ui.theme.TopBarContentColor

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
        title = {
            if (showTitle) {
                Text(text = title)
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.action_back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopBarBackgroundLight,
            titleContentColor = TopBarContentColor,
            navigationIconContentColor = TopBarContentColor,
            actionIconContentColor = TopBarContentColor
        ),
        actions = {
            IconButton(onClick = onMapClick) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = stringResource(id = R.string.action_open_map)
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(id = R.string.action_share)
                )
            }
        }
    )
}