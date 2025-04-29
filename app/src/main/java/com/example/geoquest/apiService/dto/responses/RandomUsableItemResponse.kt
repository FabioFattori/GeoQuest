package com.example.geoquest.apiService.dto.responses

import com.example.geoquest.business.models.UsableItem

data class RandomUsableItemResponse(
    val message: String,
    val usableItem: UsableItem
)
