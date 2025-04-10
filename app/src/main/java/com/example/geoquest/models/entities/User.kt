package com.example.geoquest.models.entities

data class User(
    val userId: Int,
    val playerId: Int,
    val email: String
) : Model(userId)
