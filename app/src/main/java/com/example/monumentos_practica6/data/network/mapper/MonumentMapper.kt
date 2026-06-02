package com.example.monumentos_practica6.data.network.mapper

import com.example.monumentos_practica6.data.domain.model.MonumentBo
import com.example.monumentos_practica6.data.network.model.MonumentDto

fun MonumentDto.toDomain(): MonumentBo {
    val deducedCountry = if (this.city.equals("París", ignoreCase = true)) "France" else "Spain"

    return MonumentBo(
        id = this.id,
        name = this.name,
        description = this.description,
        country = deducedCountry,
        city = this.city,
        location = "",
        latitude = this.location.latitude,
        longitude = this.location.longitude,
        images = this.images.map { it.url },
        wikiUrl = this.urlExtraInformation ?: "",
        creationDate = System.currentTimeMillis(),
        isFavorite = false
    )
}