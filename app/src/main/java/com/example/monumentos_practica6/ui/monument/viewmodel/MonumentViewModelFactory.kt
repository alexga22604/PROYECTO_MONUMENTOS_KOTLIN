package com.example.monumentos_practica6.ui.monument.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monumentos_practica6.data.local.MyMonumentPreferences
import com.example.monumentos_practica6.data.repository.MonumentRepository

class MonumentViewModelFactory(
    private val repository: MonumentRepository,
    private val preferences: MyMonumentPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonumentViewModel::class.java)) {
            return MonumentViewModel(repository, preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}