package com.example.monumentos_practica6.ui.monument_map.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
fun MonumentMapTopBar(
    onMenuClick: () -> Unit,
    title: String = stringResource(id = R.string.monuments_title)
) {
    TopAppBar(
        title = { Text(text = title) },
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
        )
    )
}