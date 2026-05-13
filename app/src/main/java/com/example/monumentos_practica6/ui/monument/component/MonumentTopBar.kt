package com.example.monumentos_practica6.ui.monument.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SwapVert
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
fun MonumentTopBar(
    onMenuClick: () -> Unit,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.monuments_title)) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.menu_description)
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
            IconButton(onClick = onSortClick) {
                Icon(
                    imageVector = Icons.Default.SwapVert,
                    contentDescription = stringResource(id = R.string.sort_description)
                )
            }
            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = stringResource(id = R.string.filter_description)
                )
            }
        }
    )
}