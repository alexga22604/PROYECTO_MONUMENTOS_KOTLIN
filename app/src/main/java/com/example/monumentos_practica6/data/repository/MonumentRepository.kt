package com.example.monumentos_practica6.data.repository

import com.example.monumentos_practica6.data.domain.model.MonumentBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MonumentRepository {
    fun getMonuments(): Flow<List<MonumentBo>> = flow {
        val monuments = listOf(
            MonumentBo(
                id = 1,
                name = "Sagrada Familia",
                city = "Barcelona",
                location = "Carrer de Mallorca, 401",
                description = "The Basílica i Temple Expiatori de la Sagrada Família is a large unfinished Roman Catholic minor basilica in Barcelona, designed by Catalan architect Antoni Gaudí.",
                imageUrl = "https://www.recordrentacar.com/blog/wp-content/uploads/2025/10/Copia-de-Diseno-sin-titulo-5-1080x624.png",
                country = "Spain",
                creationDate = -2776982400000L,
                latitude = 41.4036,
                longitude = 2.1744
            ),
            MonumentBo(
                id = 2,
                name = "Catedral de Burgos",
                city = "Burgos",
                location = "Plaza de Santa María, s/n",
                description = "The Cathedral of Saint Mary of Burgos is a Catholic church dedicated to the Virgin Mary located in the historical center of the Spanish city of Burgos.",
                imageUrl = "https://www.infobae.com/resizer/v2/GI74U23BCVCTTKGZW65Y3LMZMM.jpg?auth=479724c27d2c0159347a1df1e8eed3f4f6daa187f5e4aafe495d8a56bd374312&smart=true&width=1200&height=675&quality=85",
                country = "Spain",
                creationDate = -23627424000000L,
                latitude = 42.3406,
                longitude = -3.7042
            )
        )
        emit(monuments)
    }
}