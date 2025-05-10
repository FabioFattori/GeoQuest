package com.example.geoquest.apiService

import com.example.geoquest.apiService.dto.requests.CheckTokenParams
import com.example.geoquest.apiService.dto.requests.CreateCompletedQuestRequest
import com.example.geoquest.apiService.dto.requests.CreatePoiRequest
import com.example.geoquest.apiService.dto.requests.LoginParams
import com.example.geoquest.apiService.dto.requests.NewUser
import com.example.geoquest.apiService.dto.requests.CreateRandomItemRequest
import com.example.geoquest.apiService.dto.requests.UpdatePlayerRequest
import com.example.geoquest.apiService.dto.responses.OnlyMessageResponse
import com.example.geoquest.apiService.dto.responses.RandomUsableItemResponse
import com.example.geoquest.apiService.dto.responses.RegisterAndLoginResponse
import com.example.geoquest.apiService.dto.responses.UpdatePlayerResponse
import com.example.geoquest.business.models.CollectedPoi
import com.example.geoquest.business.models.CompletedQuest
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.UsableItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @PUT("player/{id}")
    suspend fun updatePlayer(@Path("id") playerId:Int, @Body data: UpdatePlayerRequest) : Response<UpdatePlayerResponse>

    @POST("usableItems/createRandomUsableItem")
    suspend fun createRandomUsableItem(@Body data: CreateRandomItemRequest): Response<RandomUsableItemResponse>

    @POST("equippableItems")
    suspend fun createRandomEquippableItem(@Body data: CreateRandomItemRequest): Response<EquippableItem>

    @GET("usableItems/getUsableItemsOfUser")
    suspend fun getUsableItemInventory(@Query("ownerId") userId: Int): Response<List<UsableItem>>

    @POST("collectedPois/create")
    suspend fun collectPoi(@Body data : CreatePoiRequest) : Response<CollectedPoi>

    @GET("collectedPois/getAll")
    suspend fun getAllCollectedPoi(@Query("playerId") id: Int) : Response<List<CollectedPoi>>

    @DELETE("usableItems/{id}")
    suspend fun deleteUsableItem(@Path("id") id: Int) : Response<OnlyMessageResponse>

    @GET("inventory")
    suspend fun getInventory(
        @Query("ownerId") ownerId: Int,
        @Query("type") type: Int
    ): Response<List<EquippableItem>>

    @GET("usableItems/{id}")
    suspend fun getUsableItemById(@Path("id") id: Int) : Response<UsableItem>

    @GET("equippableItems/{id}")
    suspend fun getEquippableItemById(@Path("id") id: Int) : Response<EquippableItem>

    @GET("completedQuests/getAll")
    suspend fun getAllCompletedQuests(@Query("playerId") playerId: Int) : Response<List<CompletedQuest>>

    @POST("completedQuests/create")
    suspend fun createCompletedQuest(@Body data: CreateCompletedQuestRequest) : Response<CompletedQuest>

    @PUT("usableItems/{id}")
    suspend fun ownUsableItem(@Path("id") itemId:Int,@Body ownerId: Int) : Response<UsableItem>

    @PUT("equippableItems/{id}")
    suspend fun ownEquippableItem(@Path("id") itemId:Int,@Body ownerId: Int) : Response<EquippableItem>
}
