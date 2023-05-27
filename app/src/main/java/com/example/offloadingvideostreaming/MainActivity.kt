@file:Suppress("DEPRECATION")

package com.example.offloadingvideostreaming

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.offloadingvideostreaming.databinding.ActivityMainBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MainActivity : AppCompatActivity() {
    var videoURL =
        "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
    lateinit var player: SimpleExoPlayer
    var playWhenReady = true
    var currentWindow = 0
    var playBackPosition = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun initVideo() {
        player = ExoPlayerFactory.newSimpleInstence(this)
        PlayerView.player = player
        var uri = Uri.parse(videoURL)
        var dataSource = DefaultDataSourceFactory(this, "exoplayer-codelab")
        var media = ProgressiveMediaSource.Factory(dataSource).createMediaSource(uri)
        player.playWhenReady = playWhenReady
        player.seekTo(currentWindow, playBackPosition.toLong())
        player.prepare(media, false, false)
    }

    fun releaseVideo() {
        if (player != null) {
            playWhenReady = player.playWhenReady
            playBackPosition = player.currentPosition.toInt()
            currentWindow = player.currentWindowIndex
            player.release()
        }
    }

    override fun onStart() {
        super.onStart()
        initVideo()
    }

    override fun onResume() {
        super.onResume()
        if (player != null) {
            initVideo()
        }
    }

    override fun onPause() {
        super.onPause()
        releaseVideo()
    }

    override fun onStop() {
        super.onStop()
        releaseVideo()
    }
}