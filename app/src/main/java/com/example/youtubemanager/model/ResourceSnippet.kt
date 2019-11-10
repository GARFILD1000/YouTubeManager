package com.example.youtubemanager.model

import java.util.*

class ResourceSnippet{
    var publishedAt = ""
    var title = ""
    var description = ""
    var thumbnails = HashMap<String, ResourceThumbnail>()

    var channelId = ""
    var channelTitle = ""
    var tags = LinkedList<String>()
    var categoryId: Int = 0

    var liveBroadcastContent = ""
    var defaultLanguage: String = ""
    var defaultAudioLanguage = ""
    var localized = LocalizedDescription()

    class LocalizedDescription{
        var title = ""
        var description = ""
    }
}