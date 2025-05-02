package com.example.geoquest.apiService

import com.example.geoquest.apiService.dto.requests.CheckTokenParams
import com.example.geoquest.apiService.dto.requests.LoginParams
import com.example.geoquest.apiService.dto.requests.NewUser
import com.example.geoquest.apiService.dto.requests.CreateRandomItemRequest
import com.example.geoquest.apiService.dto.responses.OnlyMessageResponse
import com.example.geoquest.apiService.dto.responses.RandomUsableItemResponse
import com.example.geoquest.apiService.dto.responses.RegisterAndLoginResponse
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.UsableItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST("usableItems/createRandomUsableItem")
    suspend fun createRandomUsableItem(@Body data: CreateRandomItemRequest): Response<RandomUsableItemResponse>

    @GET("usableItems/getUsableItemsOfUser")
    suspend fun getUsableItemInventory(@Query("ownerId") userId: Int) : Response<List<UsableItem>>

    @GET("equippableItems/getInventory")
    suspend fun getInventory(
        @Query("ownerId") ownerId: Int,
        @Query("type") type: Int
    ) : Response<List<EquippableItem>>
}
