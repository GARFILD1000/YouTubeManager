package com.example.youtubemanager.model

import java.util.HashMap

class Resource{
    var kind = ""
    var etag = ""
    var id: ResourceId? = null
    var snippet: ResourceSnippet? = null

    class ResourceId{
        var kind = ""
        var videoId = ""
        var channelId = ""
        var playlistId = ""
    }
}