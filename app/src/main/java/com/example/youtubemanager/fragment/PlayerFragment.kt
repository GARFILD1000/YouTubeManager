package com.example.youtubemanager.fragment

import android.media.MediaDataSource
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.youtubemanager.R
import com.example.youtubemanager.databinding.FragmentPlayerBinding
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment: Fragment(), Player.EventListener{
    private lateinit var binding: FragmentPlayerBinding
    private lateinit var player: SimpleExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer()
    }

    private fun initPlayer(){
        player = ExoPlayerFactory.newSimpleInstance(context)
        player.addListener(this)
        player.playWhenReady = true
        playerView.player = player
    }

    fun playVideo(url: String){
        val mediaSource = createMediaSource(Uri.parse(url))
        player.prepare(mediaSource)
    }

    private fun createMediaSource(uri: Uri): MediaSource {
        val userAgent = "exoplayer-codelab"
        val lastPathSegment = uri.getLastPathSegment()!!
        if (lastPathSegment.contains("mp3") || lastPathSegment.contains("mp4")) {
            return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else if (lastPathSegment.contains("m3u8")) {
            return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else {
            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(
                DefaultHttpDataSourceFactory("ua", null))
            val manifestDataSourceFactory = DefaultHttpDataSourceFactory(userAgent)
            return DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri)
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        activity?: return
        when(playbackState){
            Player.STATE_ENDED -> {
                if (playWhenReady){
                    //do on video ended
                }
            }
        }
//        super.onPlayerStateChanged(playWhenReady, playbackState)
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

//        super.onPlaybackParametersChanged(playbackParameters)
    }
}