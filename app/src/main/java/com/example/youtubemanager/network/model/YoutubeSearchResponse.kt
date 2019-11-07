package com.example.youtubemanager.network.model

import com.example.youtubemanager.model.Resource
import java.util.*

class YoutubeSearchResponse{
    var kind = ""
    var etag = ""
    var nextPageToken = ""
    var prevPageToken = ""
    var regionCode = ""
    var pageInfo: PageInfo? = null
    var items =  LinkedList<Resource>()

    class PageInfo{
        var totalResults = 0
        var resultsPerPage = 0
    }
}