package com.example.monumentos_practica6.ui.my_monument.screen

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
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
import coil.compose.AsyncImage
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.monument.viewmodel.MonumentViewModel
import com.example.monumentos_practica6.ui.monument_map.component.MonumentMapTopBar
import com.example.monumentos_practica6.ui.my_monument.component.MyMonumentItem
import com.example.monumentos_practica6.ui.theme.MapFabDarkBlue

private const val EMPTY_IMAGE_URL = "https://cdn-icons-png.flaticon.com/512/73/73927.png"

@Composable
fun MyMonumentScreen(
    viewModel: MonumentViewModel,
    paddingValues: PaddingValues,
    onMenuClick: () -> Unit,
    onNavigateToAdd: (Double, Double) -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            MonumentMapTopBar(
                onMenuClick = onMenuClick,
                title = stringResource(id = R.string.my_monuments_title)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAdd(0.0, 0.0) },
                containerColor = MapFabDarkBlue
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        if (state.myMonuments.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = EMPTY_IMAGE_URL,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(id = R.string.without_own_monuments),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(id = R.string.do_not_be_afraid),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(state.myMonuments) { monument ->
                    MyMonumentItem(
                        monument = monument,
                        onClick = { onNavigateToDetail(monument.id) }
                    )
                    Divider(color = Color.LightGray)
                }
            }
        }
    }
}