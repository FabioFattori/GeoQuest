package com.example.geoquest.business.classes.deserializers

import com.example.geoquest.business.classes.Position
import com.example.geoquest.business.models.CollectedPoi
import com.example.geoquest.business.models.TimeStamps
import com.google.gson.*
import java.lang.reflect.Type
import java.util.Date


class CollectedPoiDeserializer : JsonDeserializer<CollectedPoi> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): CollectedPoi {
            val jsonObj = json.asJsonObject

            val id = jsonObj["id"].asInt

            val createdAt = context.deserialize<Date>(jsonObj["created_at"], Date::class.java)
            val updatedAt = context.deserialize<Date>(jsonObj["updated_at"], Date::class.java)

            val lat = jsonObj["latitude"].asDouble
            val lon = jsonObj["longitude"].asDouble

            val timeStamps = TimeStamps(createdAt, updatedAt)
            val position = Position(lat, lon)

            return CollectedPoi(id, timeStamps, position)
        }
    }
