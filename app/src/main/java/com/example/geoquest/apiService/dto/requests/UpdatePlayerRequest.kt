package com.example.geoquest.apiService.dto.requests

data class UpdatePlayerRequest(
    val name: String,
    val level: Int,
    val experienceCollected: Int,
    val nWonBattles: Int,
    val nBattles: Int,
    val helmetId: Int?,
    val weaponId: Int?,
    val runeId: Int?
)
