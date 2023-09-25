package com.dicoding.myexoplayer

import android.content.ComponentName
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.dicoding.myexoplayer.databinding.ActivityMainBinding
import com.google.common.util.concurrent.MoreExecutors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        // Create and set MediaItems for video and audio
        val videoItem = MediaItem.fromUri("https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4")
        val audioItem = MediaItem.fromUri("https://github.com/dicodingacademy/assets/raw/main/android_intermediate_academy/bensound_ukulele.mp3")

        // Prepare the player with the media items
        player.setMediaItems(listOf(videoItem, audioItem))
        player.prepare()

        hideSystemUI()
    }

    override fun onStart() {
        super.onStart()
        // Create a MediaController for playback control
        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener(
            { binding.playerView.player = controllerFuture.get() },
            MoreExecutors.directExecutor()
        )
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the ExoPlayer instance when the activity is destroyed
        player.release()
    }
}
