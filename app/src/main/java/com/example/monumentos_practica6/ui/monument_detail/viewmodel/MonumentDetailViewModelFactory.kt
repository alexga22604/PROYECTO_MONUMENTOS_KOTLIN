package com.example.monumentos_practica6.ui.monument_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monumentos_practica6.data.repository.MonumentRepository

class MonumentDetailViewModelFactory(
    private val repository: MonumentRepository,
    private val monumentId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonumentDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MonumentDetailViewModel(repository, monumentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}