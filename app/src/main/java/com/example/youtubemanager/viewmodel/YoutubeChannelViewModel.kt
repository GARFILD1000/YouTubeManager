package com.example.youtubemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubemanager.model.ChannelResource
import com.example.youtubemanager.model.Resource
import com.example.youtubemanager.model.SearchResource
import java.util.*

class YoutubeChannelViewModel: ViewModel(){
    val currentChannelLiveData = MutableLiveData<SearchResource?>()
    val channelFullInfo = MutableLiveData<ChannelResource?>()
    var channelContentLiveData = MutableLiveData<LinkedList<SearchResource>>().apply{
        postValue(LinkedList())
    }
    var nextPageToken: String? = null
    var previousPageToken: String? = null
}