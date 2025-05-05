package com.example.geoquest.business.models

data class CompletedQuest(
    val id:Int,
    val playerId:Int,
    val timeStamps: TimeStamps,
    val name: String
)
