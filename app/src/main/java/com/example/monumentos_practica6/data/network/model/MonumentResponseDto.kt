package com.example.monumentos_practica6.data.network.model

import com.google.gson.annotations.SerializedName

data class MonumentResponseDto(
    @SerializedName("monuments") val monuments: List<MonumentDto>
)

data class MonumentDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("citY") val city: String,
    @SerializedName("description") val description: String,
    @SerializedName("urlExtraInformation") val urlExtraInformation: String?,
    @SerializedName("location") val location: LocationDto,
    @SerializedName("images") val images: List<ImageDto>
)

data class LocationDto(
    @SerializedName("latitudE") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)

data class ImageDto(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String
)