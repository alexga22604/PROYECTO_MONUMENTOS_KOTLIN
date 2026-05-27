package com.example.monumentos_practica6.data.repository

import com.example.monumentos_practica6.data.domain.model.MonumentBo
import com.example.monumentos_practica6.data.network.mapper.toDomain
import com.example.monumentos_practica6.data.network.model.MonumentResponseDto
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonumentRepository @Inject constructor(
    private val gson: Gson
) {
    private val jsonMock = """
        {
          "monuments": [
            {
              "id": 1000,
              "name": "La Giralda",
              "citY": "Sevilla",
              "description": "La Giralda is the bell tower of Seville Cathedral in Seville, Spain. It was originally built as the minaret for the Great Mosque of Seville in al-Andalus, Moorish Spain.",
              "urlExtraInformation": "https://es.wikipedia.org/wiki/Giralda",
              "location": {
                "latitudE": 37.38614,
                "longitude": -5.99238
              },
              "images": [
                { "id": 1001, "url": "https://builder.livingtours.com/public/images/produtos/VhnkzgTFBDSeville_Cathedral.jpg" },
                { "id": 1002, "url": "https://img2.rtve.es/n/16924284?w=1600" },
                { "id": 1003, "url": "https://visitasevilla.es/wp-content/uploads/2025/06/shutterstock_2411044311-1024x683.jpg" }
              ]
            },
            {
              "id": 2000,
              "name": "Sagrada Familia",
              "citY": "Barcelona",
              "description": "The Basílica i Temple Expiatori de la Sagrada Família is a large unfinished Roman Catholic minor basilica in Barcelona, designed by Catalan architect Antoni Gaudí.",
              "urlExtraInformation": "https://es.wikipedia.org/wiki/Templo_Expiatorio_de_la_Sagrada_Familia",
              "location": {
                "latitudE": 41.4036,
                "longitude": 2.1744
              },
              "images": [
                { "id": 2001, "url": "https://pro.static.holabarcelonablog.tmb.cat/s3fs-public/2020-12/SF.jpg" },
                { "id": 2002, "url": "https://thebarcelonafeeling.com/wp-content/uploads/2022/12/Sagrada-Familia-interior.jpeg" },
                { "id": 2003, "url": "https://cdn.getyourguide.com/image/format=auto,fit=crop,gravity=auto,quality=60,width=400,height=265,dpr=2/tour_img/58ac5545e7492.jpeg" }
              ]
            },
            {
              "id": 3000,
              "name": "Notre Dam",
              "citY": "París",
              "description": "Se trata de uno de los edificios más señeros y antiguos de cuantos se construyeron en estilo gótico.",
              "urlExtraInformation": "https://es.wikipedia.org/wiki/Catedral_de_Notre_Dame_de_Par%C3%ADs",
              "location": {
                "latitudE": 48.852966,
                "longitude": 2.349902
              },
              "images": [
                { "id": 3001, "url": "https://files.merca20.com/uploads/2017/06/bigstock-Notre-Dame-de-Paris-France-at-16555229.jpg" },
                { "id": 3002, "url": "https://www.paris360.de/sites/default/files/styles/node/public/field/image/location/notre-dame-paris.jpg?itok=7U7gZ8so" }
              ]
            }
          ]
        }
    """.trimIndent()

    private val _monuments = MutableStateFlow<List<MonumentBo>>(emptyList())

    init {
        loadMonuments()
    }

    private fun loadMonuments() {
        val response = gson.fromJson(jsonMock, MonumentResponseDto::class.java)
        _monuments.value = response.monuments.map { it.toDomain() }
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