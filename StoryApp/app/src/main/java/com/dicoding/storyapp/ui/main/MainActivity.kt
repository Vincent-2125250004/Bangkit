package com.dicoding.storyapp.ui.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.Result
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.ui.ViewModelFactory
import com.dicoding.storyapp.ui.adapter.StoriesAdapter
import com.dicoding.storyapp.ui.add.AddActivity
import com.dicoding.storyapp.ui.welcome.WelcomeActivity
import com.google.android.material.tabs.TabLayout.TabGravity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter: StoriesAdapter = StoriesAdapter()
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager
        binding.rvStories.adapter = adapter

        viewModel.getAllStories().observe(this) { result ->
            showLoading(result is Result.Loading)
            lifecycleScope.launch {
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                        adapter.submitList(result.data)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this@MainActivity, result.error, Toast.LENGTH_SHORT).show()
                    }

                    is Result.Loading -> {

                    }
                }
            }
        }


        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {
                    viewModel.logout()
                    true
                }

                else -> {
                    false
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            val intentAdd = Intent(this, AddActivity::class.java)
            startActivity(intentAdd)
        }


    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.refreshStories()
            viewModel.refreshStatus.observe(this@MainActivity) { isRefreshed ->
                if (isRefreshed) {
                    viewModel.getAllStories()
                }
            }
        }
    }
}