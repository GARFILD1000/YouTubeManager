package com.example.youtubemanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.youtubemanager.R
import com.example.youtubemanager.model.Resource
import com.example.youtubemanager.model.SearchResource
import com.example.youtubemanager.model.VideoResource
import com.example.youtubemanager.network.GoogleApiService
import com.example.youtubemanager.network.model.YoutubeResponse
import com.example.youtubemanager.viewmodel.YoutubePlayerViewModel
import kotlinx.android.synthetic.main.fragment_play_video.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class PlayVideoFragment: Fragment(){
    private lateinit var playerViewModel: YoutubePlayerViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerViewModel = ViewModelProviders.of(activity!!).get(YoutubePlayerViewModel::class.java)
        playerViewModel.cuddentVideoLiveData.observe(this, object: Observer<SearchResource?>{
            override fun onChanged(resource: SearchResource?) {
                resource?.let{
                    setPreliminaryVideoInfo(it)
                    resource.id?.videoId?.let{
                        getFullVideoInfo(it)
                    }
                }
            }
        })
    }

    private fun getFullVideoInfo(videoId: String){
        GoogleApiService.getYoutubeService().getVideoInfo(videoId).enqueue(object: Callback<YoutubeResponse<VideoResource>>{
            override fun onFailure(call: Call<YoutubeResponse<VideoResource>>, t: Throwable) {

            }
            override fun onResponse(
                call: Call<YoutubeResponse<VideoResource>>,
                response: Response<YoutubeResponse<VideoResource>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.let { data ->
                        data.items.getOrNull(0)?.let{
                            setFullVideoInfo(it)
                        }
                    }
                }
            }
        })
    }

    fun setPreliminaryVideoInfo(resource: Resource){
        resource.snippet?.let {snippet ->
            titleView.setText(snippet.title)
            descriptionView.setText(snippet.description)
        }
    }

    fun setFullVideoInfo(resource: Resource){
        resource.snippet?.let {snippet ->
            titleView.setText(snippet.title)
            descriptionView.setText(snippet.description)
            val tagsString = StringBuilder()
            snippet.tags.forEach {
                tagsString.append(it).append(" ")
            }
            tagsView.setText(tagsString.toString())
        }
    }
}