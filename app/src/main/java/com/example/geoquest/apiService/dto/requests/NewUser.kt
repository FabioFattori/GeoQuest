package com.example.geoquest.apiService.dto.requests

data class NewUser(
    val email: String,
    val password: String,
    val playerName: String
)
