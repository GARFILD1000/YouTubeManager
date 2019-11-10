package com.example.youtubemanager.network.model

import com.example.youtubemanager.model.SearchResource
import java.util.*

class YoutubeResponse <T>{
    companion object{
        const val KIND_YOUTUBE_VIDEO_LIST = "youtube#videoListResponse"
        const val KIND_YOUTUBE_CHANNEL_LIST = "youtube#channelListResponse"
    }
    var kind = ""
    var etag = ""
    var nextPageToken = ""
    var prevPageToken = ""
    var regionCode = ""
    var pageInfo: PageInfo? = null
    var items =  LinkedList<T>()

    class PageInfo{
        var totalResults = 0
        var resultsPerPage = 0
    }
}