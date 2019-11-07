package com.example.youtubemanager.model

class ResourceThumbnail{
    companion object{
        const val TYPE_DEFAILD = "default"
        const val TYPE_MEDIUM = "medium"
        const val TYPE_HIGH = "high"
    }

    var url = ""
    var width = 0
    var height = 0
}