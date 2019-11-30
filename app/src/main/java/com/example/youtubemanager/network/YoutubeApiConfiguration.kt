package com.example.youtubemanager.network

import com.example.youtubemanager.App
import com.example.youtubemanager.R
import com.example.youtubemanager.model.ChannelResource
import com.example.youtubemanager.model.SearchResource
import com.example.youtubemanager.model.VideoResource
import com.example.youtubemanager.network.model.YoutubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiConfiguration {
    companion object {
        private val API_KEY = App.getContext().getString(R.string.api_key)
    }

    @GET("/youtube/v3/search")
    fun findVideo(
        @Query("q") searchQuery: String,
        @Query("maxResults") maxCount: Int,
        @Query("pageToken") pageToken: String?,
        @Query("part") partParameter: String = "snippet",
        @Query("key") key: String = API_KEY
    ): Call<YoutubeResponse<SearchResource>>

    @GET("/youtube/v3/videos")
    fun getVideoInfo(
        @Query("id") id: String,
        @Query("part") partParameter: String = "snippet",
        @Query("key") key: String = API_KEY
    ): Call<YoutubeResponse<VideoResource>>

    @GET("/youtube/v3/channels")
    fun getChannelInfo(
        @Query("id") channelId: String,
        @Query("part") partParameter: String = "snippet, brandingSettings",
        @Query("key") key: String = API_KEY
    ): Call<YoutubeResponse<ChannelResource>>

    @GET("/youtube/v3/search")
    fun getChannelVideos(
        @Query("channelId") channelId: String,
        @Query("order") order: String = "date",
        @Query("part") partParameter: String = "snippet",
        @Query("key") key: String = API_KEY
    ): Call<YoutubeResponse<SearchResource>>
}