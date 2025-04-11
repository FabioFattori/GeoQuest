package com.example.geoquest.apiService

import com.example.geoquest.apiService.dto.LoginParams
import com.example.geoquest.apiService.dto.NewUser
import com.example.geoquest.apiService.dto.RegisterAndLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceInterface {
    @POST("user")
    suspend fun registerUser(@Body newUser: NewUser): Response<RegisterAndLoginResponse>

    @POST("user/login")
    suspend fun loginUser(@Body user: LoginParams): Response<RegisterAndLoginResponse>
}
