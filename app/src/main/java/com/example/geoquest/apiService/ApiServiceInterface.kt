package com.example.geoquest.apiService

import com.example.geoquest.apiService.dto.CheckTokenParams
import com.example.geoquest.apiService.dto.LoginParams
import com.example.geoquest.apiService.dto.NewUser
import com.example.geoquest.apiService.dto.OnlyMessageResponse
import com.example.geoquest.apiService.dto.RegisterAndLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceInterface {
    @POST("user")
    suspend fun registerUser(@Body newUser: NewUser): Response<RegisterAndLoginResponse>

    @POST("user/login")
    suspend fun loginUser(@Body user: LoginParams): Response<RegisterAndLoginResponse>

    @POST("user/checkToken")
    suspend fun checkToken(@Body data: CheckTokenParams): Response<OnlyMessageResponse>

    @POST("user/logout")
    suspend fun logoutUser(): Response<OnlyMessageResponse>

    @DELETE("user/{id}")
    suspend fun deleteUser(@Path("id") userId: Int): Response<OnlyMessageResponse>
}
