package com.example.geoquest.business.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    @SerializedName("player")
    val playerId: Int,
    val email: String
)
