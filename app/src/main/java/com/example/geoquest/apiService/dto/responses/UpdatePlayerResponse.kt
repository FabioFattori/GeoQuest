package com.example.geoquest.apiService.dto.responses

import com.example.geoquest.business.models.Player

data class UpdatePlayerResponse(
    val message: String,
    val player: Player
)
