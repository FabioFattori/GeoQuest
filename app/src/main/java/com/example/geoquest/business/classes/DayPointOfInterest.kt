package com.example.geoquest.business.classes

data class DayPointOfInterest(
    val name: String?,
    val position: Position,
    val category: String?
)

data class Position(
    val lat: Double,
    val lon: Double
)
