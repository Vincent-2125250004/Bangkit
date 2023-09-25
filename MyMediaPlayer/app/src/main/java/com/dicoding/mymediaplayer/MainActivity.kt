package com.dicoding.mymediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mymediaplayer.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnPlay.setOnClickListener {
                if (!isReady) {
                    mMediaPlayer?.prepareAsync()
                } else {
                    if (mMediaPlayer?.isPlaying as Boolean) {
                        mMediaPlayer?.pause()
                    } else {
                        mMediaPlayer?.start()
                    }
                }
            }
            btnStop.setOnClickListener {
                if (mMediaPlayer?.isPlaying as Boolean || isReady) {
                    mMediaPlayer?.stop()
                    isReady = false
                }
            }
        }

        init()
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        mMediaPlayer?.setAudioAttributes(attribute)
        val afd = applicationContext.resources.openRawResourceFd(R.raw.kafka_theme_music)
        try {
            mMediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e : IOException) {
            e.printStackTrace()
        }

        mMediaPlayer?.setOnPreparedListener {
            isReady = true
            mMediaPlayer?.start()
        }
        mMediaPlayer?.setOnErrorListener { mp, what, extra -> false }

    }
}