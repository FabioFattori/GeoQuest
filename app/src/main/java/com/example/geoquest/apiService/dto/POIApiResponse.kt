package com.example.geoquest.apiService.dto

import com.example.geoquest.business.classes.DayPointOfInterest
import com.example.geoquest.business.classes.Position

data class POIApiResponse(
    val elements: List<SinglePOIElement>
)

data class SinglePOIElement(
    val lat: Double,
    val lon: Double,
    val tags: Map<String, String>?
) {
    fun toDayPoi(): DayPointOfInterest {
        val name = tags?.get("name")
        val category = tags?.keys?.firstOrNull {
            it in listOf(
                "historic",
                "tourism",
                "amenity",
                "leisure",
                "natural"
            )
        }
        val categoryValue = category?.let { tags[it] }
        return DayPointOfInterest(name, Position(lat, lon), categoryValue)
    }

}

