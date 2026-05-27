package com.example.monumentos_practica6.ui.monument.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monumentos_practica6.data.domain.model.MonumentBo
import com.example.monumentos_practica6.data.local.MyMonumentPreferences
import com.example.monumentos_practica6.data.repository.MonumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val EMPTY_STRING = ""

@HiltViewModel
class MonumentViewModel @Inject constructor(
    private val repository: MonumentRepository,
    private val preferences: MyMonumentPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(MonumentState())
    val uiState: StateFlow<MonumentState> = _uiState.asStateFlow()

    init {
        fetchMonuments()
        loadCustomMonuments()
    }

    private fun loadCustomMonuments() {
        val savedMonuments = preferences.getMonuments()
        _uiState.update { it.copy(myMonuments = savedMonuments) }
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

    fun toggleFavorite(id: Int) {
        repository.toggleFavorite(id)
    }

    fun addCustomMonument(
        name: String,
        description: String,
        country: String,
        city: String,
        location: String,
        latitude: Double,
        longitude: Double,
        images: List<String>
    ) {
        val newId = (_uiState.value.myMonuments.maxOfOrNull { it.id } ?: 10000) + 1

        val newMonument = MonumentBo(
            id = newId,
            name = name,
            description = description,
            country = country,
            city = city,
            location = location,
            latitude = latitude,
            longitude = longitude,
            images = images,
            wikiUrl = EMPTY_STRING,
            creationDate = System.currentTimeMillis(),
            isFavorite = false
        )

        val currentMyMonuments = _uiState.value.myMonuments.toMutableList()
        currentMyMonuments.add(newMonument)

        _uiState.update {
            it.copy(
                myMonuments = currentMyMonuments,
                newlyAddedMonumentId = newId
            )
        }

        preferences.saveMonuments(currentMyMonuments)
    }

    fun clearNewlyAddedMonument() {
        _uiState.update { it.copy(newlyAddedMonumentId = null) }
    }

    fun deleteCustomMonument(id: Int) {
        val currentMyMonuments = _uiState.value.myMonuments.filter { it.id != id }

        _uiState.update { state ->
            state.copy(myMonuments = currentMyMonuments)
        }

        preferences.saveMonuments(currentMyMonuments)
    }
}