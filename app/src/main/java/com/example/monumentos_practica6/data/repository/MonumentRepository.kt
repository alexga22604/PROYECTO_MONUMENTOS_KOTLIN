package com.example.monumentos_practica6.data.repository

import com.example.monumentos_practica6.data.domain.model.MonumentBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class MonumentRepository {
    companion object {
        private val _monuments = MutableStateFlow(
            listOf(
                MonumentBo(
                    id = 1,
                    name = "La Giralda",
                    city = "Sevilla",
                    location = "Av. de la Constitución, s/n",
                    description = "La Giralda is the bell tower of Seville Cathedral in Seville, Spain. It was originally built as the minaret for the Great Mosque of Seville in al-Andalus, Moorish Spain. La Giralda is the bell tower of Seville Cathedral in Seville, Spain. It was originally built as the minaret for the Great Mosque of Seville in al-Andalus, Moorish Spain. La Giralda is the bell tower of Seville Cathedral in Seville, Spain. It was originally built as the minaret for the Great Mosque of Seville in al-Andalus, Moorish Spain. La Giralda is the bell tower of Seville Cathedral in Seville, Spain. It was originally built as the minaret for the Great Mosque of Seville in al-Andalus, Moorish Spain.",
                    images = listOf(
                        "https://builder.livingtours.com/public/images/produtos/VhnkzgTFBDSeville_Cathedral.jpg",
                        "https://img2.rtve.es/n/16924284?w=1600",
                        "https://visitasevilla.es/wp-content/uploads/2025/06/shutterstock_2411044311-1024x683.jpg"
                    ),
                    country = "España",
                    creationDate = -1,
                    latitude = 37.38614,
                    longitude = -5.99238,
                    wikiUrl = "https://es.wikipedia.org/wiki/Giralda",
                    isFavorite = false
                ),
                MonumentBo(
                    id = 2,
                    name = "Sagrada Familia",
                    city = "Barcelona",
                    location = "Carrer de Mallorca, 401",
                    description = "The Basílica i Temple Expiatori de la Sagrada Família is a large unfinished Roman Catholic minor basilica in Barcelona, designed by Catalan architect Antoni Gaudí. The Basílica i Temple Expiatori de la Sagrada Família is a large unfinished Roman Catholic minor basilica in Barcelona, designed by Catalan architect Antoni Gaudí. The Basílica i Temple Expiatori de la Sagrada Família is a large unfinished Roman Catholic minor basilica in Barcelona, designed by Catalan architect Antoni Gaudí. The Basílica i Temple Expiatori de la Sagrada Família is a large unfinished Roman Catholic minor basilica in Barcelona, designed by Catalan architect Antoni Gaudí.",
                    images = listOf(
                        "https://pro.static.holabarcelonablog.tmb.cat/s3fs-public/2020-12/SF.jpg",
                        "https://thebarcelonafeeling.com/wp-content/uploads/2022/12/Sagrada-Familia-interior.jpeg",
                        "https://cdn.getyourguide.com/image/format=auto,fit=crop,gravity=auto,quality=60,width=400,height=265,dpr=2/tour_img/58ac5545e7492.jpeg"
                    ),
                    country = "España",
                    creationDate = -2,
                    latitude = 41.4036,
                    longitude = 2.1744,
                    wikiUrl = "https://es.wikipedia.org/wiki/Templo_Expiatorio_de_la_Sagrada_Familia",
                    isFavorite = false
                )
            )
        )
    }

    fun getMonuments(): Flow<List<MonumentBo>> = _monuments

    fun getMonumentById(id: Int): Flow<MonumentBo?> = _monuments.map { list ->
        list.find { it.id == id }
    }

    fun toggleFavorite(id: Int) {
        _monuments.update { list ->
            list.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
        }
    }
}