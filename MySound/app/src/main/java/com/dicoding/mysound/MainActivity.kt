package com.dicoding.mysound

import android.media.SoundPool
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mysound.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = SoundPool.Builder().setMaxStreams(10).build()

        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this, "Gagal Load", Toast.LENGTH_SHORT).show()
            }

        }
        soundId = sp.load(this, R.raw.glass_clinking,1)
        binding.btnSoundPool.setOnClickListener {
            if (spLoaded) {
                sp.play(soundId, 1f,1f,0,0,1f)
            }
        }
    }
}