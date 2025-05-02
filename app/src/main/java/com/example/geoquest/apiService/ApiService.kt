package com.example.geoquest.apiService

import com.example.geoquest.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL: String = BuildConfig.BASE_URL
    private const val POI_BASE_URL: String = BuildConfig.POI_API

    val retrofit: ApiServiceInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceInterface::class.java)
    }

    fun getTokenizedRequester(token: String): ApiServiceInterface {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceInterface::class.java)
    }


    val poiApi: POIApiServiceInterface by lazy {
        Retrofit.Builder()
            .baseUrl(POI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(POIApiServiceInterface::class.java)
    }
}

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}
