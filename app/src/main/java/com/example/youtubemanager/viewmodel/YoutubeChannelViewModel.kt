package com.example.youtubemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubemanager.model.ChannelResource
import com.example.youtubemanager.model.Resource
import com.example.youtubemanager.model.SearchResource

class YoutubeChannelViewModel: ViewModel(){
    val currentChannelLiveData = MutableLiveData<SearchResource?>()
    val channelFullInfo = MutableLiveData<ChannelResource?>()
}