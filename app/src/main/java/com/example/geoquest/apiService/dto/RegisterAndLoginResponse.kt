package com.example.geoquest.apiService.dto

import com.example.geoquest.business.models.Player
import com.example.geoquest.business.models.User

data class RegisterAndLoginResponse(
    val user: User,
    val player: Player,
    val token: String,
    val message: String,
)
