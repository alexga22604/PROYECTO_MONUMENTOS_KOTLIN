package com.example.monumentos_practica6.ui.launcher.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.monumentos_practica6.R
import com.example.monumentos_practica6.ui.launcher.viewmodel.LauncherViewModel

private const val LOGO_URL = "https://cdn-icons-png.flaticon.com/512/73/73927.png"

@Composable
fun LauncherScreen(
    viewModel: LauncherViewModel = viewModel(),
    onTimeout: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigateToMonuments.collect {
            onTimeout()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = LOGO_URL,
                contentDescription = stringResource(R.string.app_logo),
                modifier = Modifier
                    .size(120.dp)
                    .clickable(enabled = !isLoading) {
                        viewModel.onImageClicked()
                    },
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.monument_txt),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(48.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.Gray,
                    strokeWidth = 3.dp
                )
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    }
}