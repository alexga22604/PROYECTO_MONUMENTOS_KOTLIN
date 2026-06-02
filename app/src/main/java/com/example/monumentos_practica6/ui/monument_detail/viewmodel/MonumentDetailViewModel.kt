package com.example.monumentos_practica6.ui.monument_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monumentos_practica6.data.local.MyMonumentPreferences
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
    private val repository: MonumentRepository,
    private val preferences: MyMonumentPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(MonumentDetailState())
    val uiState: StateFlow<MonumentDetailState> = _uiState.asStateFlow()

    fun loadMonument(id: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.getMonuments().collect { monuments ->
                val monument = monuments.find { it.id == id }
                if (monument != null) {
                    val favoriteIds = preferences.getFavoriteIds()
                    val isFavorite = favoriteIds.contains(id)

                    _uiState.update {
                        it.copy(
                            monument = monument.copy(isFavorite = isFavorite),
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun toggleFavorite() {
        val currentMonument = _uiState.value.monument ?: return
        val id = currentMonument.id

        repository.toggleFavorite(id)
        val favoriteIds = preferences.getFavoriteIds().toMutableSet()
        val newFavoriteState = !currentMonument.isFavorite

        if (newFavoriteState) {
            favoriteIds.add(id)
        } else {
            favoriteIds.remove(id)
        }
        preferences.saveFavoriteIds(favoriteIds)

        _uiState.update { state ->
            state.copy(monument = currentMonument.copy(isFavorite = newFavoriteState))
        }
    }
}