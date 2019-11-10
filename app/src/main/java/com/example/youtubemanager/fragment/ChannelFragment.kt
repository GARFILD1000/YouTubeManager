package com.example.youtubemanager.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubemanager.R
import com.example.youtubemanager.activity.MainActivity
import com.example.youtubemanager.adapter.SearchAdapter
import com.example.youtubemanager.model.ChannelResource
import com.example.youtubemanager.model.ResourceThumbnail
import com.example.youtubemanager.model.SearchResource
import com.example.youtubemanager.network.GoogleApiService
import com.example.youtubemanager.network.model.YoutubeResponse
import com.example.youtubemanager.viewmodel.YoutubeChannelViewModel
import com.example.youtubemanager.viewmodel.YoutubePlayerViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_channel.*
import kotlinx.android.synthetic.main.fragment_youtube_player.*
import retrofit2.Call
import retrofit2.Response
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import com.squareup.picasso.Callback as PicassoCallback
import retrofit2.Callback
import java.lang.Exception


class ChannelFragment : Fragment() {
    lateinit var channelViewModel: YoutubeChannelViewModel
    lateinit var playerViewModel: YoutubePlayerViewModel

    private var videoListAdapter = SearchAdapter().apply{
        onItemClick = {
            playerViewModel.cuddentVideoLiveData.postValue(it)
            (context as MainActivity).goWatchVideo()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        channelViewModel = ViewModelProviders.of(activity!!).get(YoutubeChannelViewModel::class.java)
        playerViewModel = ViewModelProviders.of(activity!!).get(YoutubePlayerViewModel::class.java)
        channelViewModel.currentChannelLiveData.observe(this, object : Observer<SearchResource?>{
            override fun onChanged(resource: SearchResource?) {
                resource?.let{
                    setPreliminaryChannelInfo(it)
                    getFullChannelInfo(it.id!!.channelId)
                }
            }
        })

        videoList.apply {
            adapter = videoListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getFullChannelInfo(channelId: String){
        GoogleApiService.getYoutubeService()
            .getChannelInfo(channelId)
            .enqueue(object: Callback<YoutubeResponse<ChannelResource>>{
                override fun onFailure(call: Call<YoutubeResponse<ChannelResource>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<YoutubeResponse<ChannelResource>>,
                    response: Response<YoutubeResponse<ChannelResource>>
                ) {
                    response.body()?.items?.getOrNull(0)?.let{
                        setFullChannelInfo(it)
                        getLatestChannelVideos(it.id)
                    }
                }

            })
    }

    private fun setPreliminaryChannelInfo(channelResource: SearchResource){
        channelTitleView.text = channelResource.snippet?.title
    }

    private fun setFullChannelInfo(channelResource: ChannelResource){
        channelTitleView.text = channelResource.brandingSettings?.channel?.title
        channelResource.snippet?.thumbnails?.get(ResourceThumbnail.TYPE_DEFAULT)?.let{
            Picasso.get().load(it.url).into(channelPreview, object : PicassoCallback {
                override fun onError(e: Exception?) {}
                override fun onSuccess() {
                    val imageBitmap = (channelPreview.getDrawable() as BitmapDrawable).bitmap
                    val imageDrawable = RoundedBitmapDrawableFactory.create(resources, imageBitmap)
                    imageDrawable.isCircular = true
                    imageDrawable.cornerRadius =
                        Math.max(imageBitmap.width, imageBitmap.height) / 2.0f
                    channelPreview.setImageDrawable(imageDrawable)
                }
            })
        }

    }

    private fun getLatestChannelVideos(channelId: String){
        GoogleApiService.getYoutubeService()
            .getChannelVideos(channelId)
            .enqueue(object: Callback<YoutubeResponse<SearchResource>>{
                override fun onFailure(call: Call<YoutubeResponse<SearchResource>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<YoutubeResponse<SearchResource>>,
                    response: Response<YoutubeResponse<SearchResource>>
                ) {
                    response.body()?.items?.let{
                        setLatestChannelVideos(it)
                    }
                }
            })
    }

    private fun setLatestChannelVideos(videoResource: List<SearchResource>){
        videoListAdapter.setItems(videoResource)
    }
}