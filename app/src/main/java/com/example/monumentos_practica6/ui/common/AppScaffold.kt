package com.example.monumentos_practica6.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun AppScaffold(
    showNavigationElements: Boolean,
    onNavigate: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            if (showNavigationElements) {
            }
        },
        bottomBar = {
            if (showNavigationElements) {
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}