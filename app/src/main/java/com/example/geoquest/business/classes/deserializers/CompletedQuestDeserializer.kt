package com.example.geoquest.business.classes.deserializers

import com.example.geoquest.business.models.CompletedQuest
import com.example.geoquest.business.models.TimeStamps
import com.google.gson.*
import java.lang.reflect.Type
import java.util.Date


class CompletedQuestDeserializer : JsonDeserializer<CompletedQuest> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CompletedQuest {
        val jsonObj = json.asJsonObject

        val id = jsonObj["id"].asInt

        val createdAt = context.deserialize<Date>(jsonObj["created_at"], Date::class.java)
        val updatedAt = context.deserialize<Date>(jsonObj["updated_at"], Date::class.java)

        val playerId = jsonObj["playerId"].asInt
        val name = jsonObj["name"].asString

        val timeStamps = TimeStamps(createdAt, updatedAt)

        return CompletedQuest(
            id = id,
            name = name,
            playerId = playerId,
            timeStamps = timeStamps
        )
    }
}
