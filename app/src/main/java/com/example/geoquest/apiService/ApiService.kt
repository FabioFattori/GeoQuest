package com.example.geoquest.apiService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL : String = "http://10.201.107.49:81/api/"

    val retrofit : ApiServiceInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceInterface::class.java)
    }
}