package com.example.youtubemanager.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GoogleApiService{
    const val API_BASE_URL = "https://www.googleapis.com/"
    const val API_CONNECTION_TIMEOUT = 2L

    private val gsonConverterFactory = GsonConverterFactory.create()

    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(API_CONNECTION_TIMEOUT, TimeUnit.MINUTES)
        .writeTimeout(API_CONNECTION_TIMEOUT, TimeUnit.MINUTES)
        .readTimeout(API_CONNECTION_TIMEOUT, TimeUnit.MINUTES)
        .addInterceptor { chain ->
            val request = chain.request()
            chain.proceed(request)
        }
        .build()

    private var retrofitInstance = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()

    fun getYoutubeService(): YoutubeApiConfiguration{
        return retrofitInstance.create(YoutubeApiConfiguration::class.java)
    }

}