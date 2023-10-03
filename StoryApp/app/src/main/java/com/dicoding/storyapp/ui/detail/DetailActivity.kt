package com.dicoding.storyapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.storyapp.api.response.ListStoryItem
import com.dicoding.storyapp.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var stories: ListStoryItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stories = intent.getParcelableExtra("varStories")!!

        stories.let { stories ->
            binding.apply {
                tvDetailName.text = stories.name
                tvDetailDescription.text = stories.description
                Glide.with(ivDetailPhoto.context).load(stories.photoUrl).into(ivDetailPhoto)
            }
        }
    }
}