package com.example.geoquest.apiService

import com.example.geoquest.apiService.dto.POIApiResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface POIApiServiceInterface {
    @POST("interpreter")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    suspend fun getPoints(@Body query: RequestBody): Response<POIApiResponse>
}
