package com.example.youtubemanager.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.youtubemanager.R
import com.example.youtubemanager.model.SearchResource
import com.example.youtubemanager.viewmodel.YoutubePlayerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_youtube_player.*

class YoutubePlayerFragment : Fragment(), YouTubePlayerListener {
    private val LOG_TAG = "YoutubePlayerFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_youtube_player, container, false)
    }

    private var youTubePlayer: YouTubePlayer? = null
    private lateinit var playerViewModel: YoutubePlayerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerViewModel = ViewModelProviders.of(activity!!)
            .get(YoutubePlayerViewModel::class.java)
        playerViewModel.cuddentVideoLiveData.observe(viewLifecycleOwner, object: Observer<SearchResource?>{
            override fun onChanged(data: SearchResource?) {
                data?.id?.videoId?.let{
                    youTubePlayer?.loadVideo(it, 0f)
                }
            }
        })
        youtubePlayerView.addYouTubePlayerListener(this)
        youtubePlayerView.getPlayerUiController().apply {
            showYouTubeButton(false)
            showUi(true)
            showVideoTitle(false)
        }
    }

    override fun onApiChange(youTubePlayer: YouTubePlayer) {

    }

    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
        Log.d(LOG_TAG, "Error: ${error.name}")
    }

    override fun onPlaybackQualityChange(
        youTubePlayer: YouTubePlayer,
        playbackQuality: PlayerConstants.PlaybackQuality
    ) {
        Log.d(LOG_TAG, "Playback quality changed: ${playbackQuality.name}")
    }

    override fun onPlaybackRateChange(
        youTubePlayer: YouTubePlayer,
        playbackRate: PlayerConstants.PlaybackRate
    ) {
        Log.d(LOG_TAG, "Playback quality changed: ${playbackRate.name}")
    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
        Log.d(LOG_TAG, "Player ready")
        this.youTubePlayer = youTubePlayer
        playerViewModel.cuddentVideoLiveData.value?.id?.videoId?.let{
            if (it.isNotEmpty()) {
                youTubePlayer.loadVideo(it,0f)
            }
        }
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
        Log.d(LOG_TAG, "Player state changed: ${state}")
    }

    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
        Log.d(LOG_TAG, "Player video duration: $duration")
    }

    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
        Log.d(LOG_TAG, "Player videoId: $videoId")
    }

    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
        Log.d(LOG_TAG, "Player video loaded fraction: ${loadedFraction}")
    }


    override fun onDestroy() {
        youTubePlayer?.pause()
        youTubePlayer?.removeListener(this)
        youtubePlayerView?.release()
        super.onDestroy()
    }
}