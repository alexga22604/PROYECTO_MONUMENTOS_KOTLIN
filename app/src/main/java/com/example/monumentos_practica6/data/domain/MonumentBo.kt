package com.example.monumentos_practica6.data.domain.model

data class MonumentBo(
    val id: Int,
    val name: String,
    val description: String,
    val city: String,
    val location: String,
    val imageUrl: String,
    val country: String,
    val creationDate: Long,
    val latitude: Double,
    val longitude: Double
)