package com.example.monumentos_practica6.ui.monument_add.state

import android.net.Uri

data class MonumentAddState(
    val name: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val country: String = EMPTY_STRING,
    val city: String = EMPTY_STRING,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val images: List<Uri> = emptyList(),
    val isSaved: Boolean = false
) {
    companion object {
        const val EMPTY_STRING = ""
    }
}