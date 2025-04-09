package com.example.geoquest.apiService

import com.example.geoquest.models.entities.Player
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServiceInterface {
    @GET("player/all")
    suspend fun getPlayers(): List<Player>

    @POST("player")
    suspend fun createPlayer(@Body player: Player): Response<Player>
}
