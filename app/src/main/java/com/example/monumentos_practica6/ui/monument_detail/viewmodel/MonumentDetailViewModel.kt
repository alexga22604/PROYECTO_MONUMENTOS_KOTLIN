package com.example.monumentos_practica6.ui.monument_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monumentos_practica6.data.repository.MonumentRepository
import com.example.monumentos_practica6.ui.monument_detail.state.MonumentDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonumentDetailViewModel @Inject constructor(
    private val repository: MonumentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MonumentDetailState())
    val uiState: StateFlow<MonumentDetailState> = _uiState.asStateFlow()

    fun loadMonument(monumentId: Int) {
        viewModelScope.launch {
            repository.getMonumentById(monumentId).collect { monument ->
                _uiState.update {
                    it.copy(
                        monument = monument,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun toggleFavorite() {
        _uiState.value.monument?.id?.let { id ->
            repository.toggleFavorite(id)
        }
    }
}