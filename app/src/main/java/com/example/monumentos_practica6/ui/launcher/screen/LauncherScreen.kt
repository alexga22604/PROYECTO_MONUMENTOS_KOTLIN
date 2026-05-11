package com.example.monumentos_practica6.ui.launcher.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.monumentos_practica6.ui.launcher.viewmodel.LauncherViewModel

private const val LOGO_URL = "https://cdn-icons-png.flaticon.com/512/73/73927.png"

@Composable
fun LauncherScreen(
    viewModel: LauncherViewModel = viewModel(),
    onTimeout: () -> Unit
) {
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
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Monuments App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(48.dp))

            CircularProgressIndicator(
                color = Color.Gray,
                strokeWidth = 3.dp
            )
        }
    }
}