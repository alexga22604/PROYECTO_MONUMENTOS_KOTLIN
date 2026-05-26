package com.example.monumentos_practica6.ui.monument_detail.state

import com.example.monumentos_practica6.data.domain.model.MonumentBo

data class MonumentDetailState(
    val monument: MonumentBo? = null,
    val isLoading: Boolean = true
)