package com.example.geoquest.business.models

import com.example.geoquest.business.classes.deserializers.CompletedQuestDeserializer
import com.google.gson.annotations.JsonAdapter

//@JsonAdapter(CompletedQuestDeserializer::class)
data class CompletedQuest(
    val id:Int,
    val playerId:Int,
    val timeStamps: TimeStamps,
    val name: String
)
