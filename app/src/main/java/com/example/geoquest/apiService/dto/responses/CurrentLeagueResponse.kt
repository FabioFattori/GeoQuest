package com.example.geoquest.apiService.dto.responses

import com.example.geoquest.business.models.Player

data class CurrentLeagueResponse(
    val players: List<Player>,
    val position: Int
)
