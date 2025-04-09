package com.example.geoquest.apiService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.geoquest.BuildConfig

object ApiService {
    private const val BASE_URL: String = BuildConfig.BASE_URL

    val retrofit : ApiServiceInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceInterface::class.java)
    }
}
