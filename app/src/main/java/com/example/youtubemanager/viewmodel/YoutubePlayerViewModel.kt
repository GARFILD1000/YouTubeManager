package com.example.youtubemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubemanager.model.SearchResource
import com.example.youtubemanager.model.VideoResource

class YoutubePlayerViewModel: ViewModel() {
    var cuddentVideoLiveData = MutableLiveData<SearchResource?>()
    var videoFullInfo = MutableLiveData<VideoResource?>()
}