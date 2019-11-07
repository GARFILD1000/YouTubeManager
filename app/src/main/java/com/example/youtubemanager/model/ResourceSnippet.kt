package com.example.youtubemanager.model

import java.util.HashMap

class ResourceSnippet{
    var publishedAt = ""
    var channelId = ""
    var title = ""
    var description = ""
    var thumbnails = HashMap<String, ResourceThumbnail>()
    var channelTitle = ""
    var liveBroadcastContent = ""
}