package com.example.geoquest.apiService.dto.requests

data class CreatePoiRequest(
    val playerId : Int,
    val latitude: Double,
    val longitude : Double
)
