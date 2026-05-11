package com.example.monumentos_practica6.ui.launcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

private const val SPLASH_DELAY = 2500L

class LauncherViewModel : ViewModel() {
    private val _navigateToMonuments = MutableSharedFlow<Unit>()
    val navigateToMonuments: SharedFlow<Unit> = _navigateToMonuments.asSharedFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            _navigateToMonuments.emit(Unit)
        }
    }
}