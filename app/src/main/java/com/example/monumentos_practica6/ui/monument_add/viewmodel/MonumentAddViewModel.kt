package com.example.monumentos_practica6.ui.monument_add.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.monumentos_practica6.ui.monument_add.state.MonumentAddState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class MonumentAddViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MonumentAddState())
    val uiState: StateFlow<MonumentAddState> = _uiState.asStateFlow()

    fun initializeLocation(lat: Double, lng: Double) {
        if (_uiState.value.latitude == 0.0 && _uiState.value.longitude == 0.0) {
            _uiState.update { it.copy(latitude = lat, longitude = lng) }
        }
    }

    fun updateName(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun updateDescription(newDescription: String) {
        if (newDescription.length <= MAX_DESCRIPTION_LENGTH) {
            _uiState.update { it.copy(description = newDescription) }
        }
    }

    fun updateCountry(newCountry: String) {
        _uiState.update { it.copy(country = newCountry) }
    }

    fun updateCity(newCity: String) {
        _uiState.update { it.copy(city = newCity) }
    }

    fun updateLocation(lat: Double, lng: Double) {
        _uiState.update { it.copy(latitude = lat, longitude = lng) }
    }

    fun addImages(newImages: List<Uri>) {
        _uiState.update { it.copy(images = it.images + newImages) }
    }

    fun removeImage(imageUri: Uri) {
        _uiState.update { it.copy(images = it.images.filter { uri -> uri != imageUri }) }
    }

    fun saveToMyMonuments() {
        val currentState = _uiState.value
        if (isValidForm(currentState)) {
            val generatedId = UUID.randomUUID().toString()
            _uiState.update { it.copy(isSaved = true) }
        }
    }

    fun resetSaveState() {
        _uiState.update { it.copy(isSaved = false) }
    }

    private fun isValidForm(state: MonumentAddState): Boolean {
        return state.name.isNotBlank() &&
                state.description.isNotBlank() &&
                state.country.isNotBlank() &&
                state.city.isNotBlank() &&
                state.images.isNotEmpty()
    }

    companion object {
        private const val MAX_DESCRIPTION_LENGTH = 250
    }
}