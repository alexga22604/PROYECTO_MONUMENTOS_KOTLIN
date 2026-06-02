package com.example.monumentos_practica6.ui.monument_favourite.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel
import com.example.monumentos_practica6.ui.monument_favourite.component.MonumentFavouriteItem
import com.example.monumentos_practica6.ui.monument_map.component.MonumentMapTopBar

@Composable
fun MonumentFavouriteScreen(
    viewModel: MonumentViewModel,
    paddingValues: PaddingValues,
    onMenuClick: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            MonumentMapTopBar(
                onMenuClick = onMenuClick,
                title = stringResource(id = R.string.favorites_title)
            )
        }
    ) { innerPadding ->
        if (state.favoriteMonuments.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.StarBorder,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(id = R.string.no_added_favorites),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.the_star_is_your_friend),
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(state.favoriteMonuments) { monument ->
                    MonumentFavouriteItem(
                        monument = monument,
                        onClick = { onNavigateToDetail(monument.id) }
                    )
                    Divider(color = Color.LightGray)
                }
            }
        }
    }
}