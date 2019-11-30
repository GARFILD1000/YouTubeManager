package com.example.youtubemanager.model

abstract class Resource{
    companion object{
        const val KIND_YOUTUBE_VIDEO = "youtube#video"
        const val KIND_YOUTUBE_CHANNEL = "youtube#channel"
    }

    var kind = ""
    var etag = ""
    var snippet: ResourceSnippet? = null

    class ResourceId{
        var kind = ""
        var videoId = ""
        var channelId = ""
        var playlistId = ""
    }

}