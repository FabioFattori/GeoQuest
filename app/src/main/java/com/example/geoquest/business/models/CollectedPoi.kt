package com.example.geoquest.business.models

import com.example.geoquest.business.classes.Position
import com.example.geoquest.business.classes.deserializers.CollectedPoiDeserializer
import com.google.gson.annotations.JsonAdapter

@JsonAdapter(CollectedPoiDeserializer::class)
data class CollectedPoi(
    val id: Int,
    val timeStamps: TimeStamps,
    val position : Position
)