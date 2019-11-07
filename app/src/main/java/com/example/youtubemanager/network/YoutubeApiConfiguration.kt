package com.example.youtubemanager.network

import androidx.core.content.ContextCompat
import com.example.youtubemanager.App
import com.example.youtubemanager.R
import com.example.youtubemanager.network.model.YoutubeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiConfiguration{
    companion object{
        private val API_KEY = App.getContext().getString(R.string.api_key)
    }


    @GET("/youtube/v3/search")
    fun findVideo(@Query("q") searchQuery: String,
        @Query("maxResults") maxCount: Int,
        @Query("pageToken") pageToken: String?,
        @Query("part") partParameter: String = "snippet",
        @Query("key") key: String = API_KEY): Call<YoutubeSearchResponse>



}