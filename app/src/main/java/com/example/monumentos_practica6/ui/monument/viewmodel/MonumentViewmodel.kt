package com.example.monumentos_practica6.ui.monument.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monumentos_practica6.data.repository.MonumentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonumentViewModel(private val repository: MonumentRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MonumentState())
    val uiState: StateFlow<MonumentState> = _uiState.asStateFlow()

    init {
        fetchMonuments()
    }

    private fun fetchMonuments() {
        viewModelScope.launch {
            repository.getMonuments().collect { monumentList ->
                val countries = monumentList.map { it.country }.distinct().sorted()

                _uiState.update {
                    it.copy(
                        monuments = monumentList,
                        availableCountries = countries
                    )
                }
                applyFiltersAndSort()
            }
        }
    }

    fun setSortType(sortType: SortType) {
        _uiState.update { it.copy(sortType = sortType) }
        applyFiltersAndSort()
    }

    fun setCountryFilter(country: String?) {
        _uiState.update { it.copy(selectedCountry = country) }
        applyFiltersAndSort()
    }

    private fun applyFiltersAndSort() {
        val currentState = _uiState.value
        var currentList = currentState.monuments

        if (currentState.selectedCountry != null) {
            currentList = currentList.filter { it.country == currentState.selectedCountry }
        }

        currentList = when (currentState.sortType) {
            SortType.DATE -> currentList.sortedBy { it.creationDate }
            SortType.NAME -> currentList.sortedBy { it.name }
            SortType.NORTH_SOUTH -> currentList.sortedByDescending { it.latitude }
            SortType.EAST_WEST -> currentList.sortedByDescending { it.longitude }
        }

        _uiState.update { it.copy(filteredMonuments = currentList) }
    }
}