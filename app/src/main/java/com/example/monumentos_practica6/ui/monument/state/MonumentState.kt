package com.example.monumentos_practica6.ui.monument.viewmodel

import com.example.monumentos_practica6.data.domain.model.MonumentBo

enum class SortType {
    DATE, NAME, NORTH_SOUTH, EAST_WEST
}

data class MonumentState(
    val monuments: List<MonumentBo> = emptyList(),
    val filteredMonuments: List<MonumentBo> = emptyList(),
    val myMonuments: List<MonumentBo> = emptyList(),
    val sortType: SortType = SortType.DATE,
    val selectedCountry: String? = null,
    val availableCountries: List<String> = emptyList(),
    val newlyAddedMonumentId: Int? = null
)