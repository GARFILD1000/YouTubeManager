package com.example.youtubemanager.model

class ResourceThumbnail{
    companion object{
        const val TYPE_DEFAULT = "default"
        const val TYPE_MEDIUM = "medium"
        const val TYPE_HIGH = "high"

        const val TYPE_STANDARD = "standard"
        const val TYPE_MAXRES = "maxres"
    }

    var url = ""
    var width = 0
    var height = 0
}