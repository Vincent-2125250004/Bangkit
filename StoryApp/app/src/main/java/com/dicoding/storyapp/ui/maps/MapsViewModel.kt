package com.dicoding.storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.api.response.ListStoryItem
import com.dicoding.storyapp.data.Result
import com.dicoding.storyapp.data.UserRepository

class MapsViewModel(private val repository: UserRepository) : ViewModel() {
    fun storiesLocation(): LiveData<Result<List<ListStoryItem>>> {
        return repository.storyLocation()
    }
}