package com.example.monumentos_practica6.ui.launcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val LOADING_DELAY = 1500L

class LauncherViewModel : ViewModel() {
    private val _navigateToMonuments = MutableSharedFlow<Unit>()
    val navigateToMonuments: SharedFlow<Unit> = _navigateToMonuments.asSharedFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    fun onImageClicked() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            delay(LOADING_DELAY)
            _navigateToMonuments.emit(Unit)
        }
    }
}