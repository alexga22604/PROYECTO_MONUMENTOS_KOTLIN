package com.example.monumentos_practica6.ui.monument.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monumentos_practica6.data.repository.MonumentRepository

class MonumentViewModelFactory(
    private val repository: MonumentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonumentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MonumentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}