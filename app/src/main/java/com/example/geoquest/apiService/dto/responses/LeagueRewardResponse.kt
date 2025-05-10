package com.example.geoquest.apiService.dto.responses

import com.example.geoquest.business.models.EquippableItem

data class LeagueRewardResponse(
    val message: String,
    val reward: EquippableItem
)
