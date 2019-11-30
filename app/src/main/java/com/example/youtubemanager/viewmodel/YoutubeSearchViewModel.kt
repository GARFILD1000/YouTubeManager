package com.example.youtubemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.youtubemanager.model.SearchResource
import java.util.*

class YoutubeSearchViewModel: ViewModel(){
    var searchLiveData = MutableLiveData<LinkedList<SearchResource>>().apply{
        postValue(LinkedList())
    }
}