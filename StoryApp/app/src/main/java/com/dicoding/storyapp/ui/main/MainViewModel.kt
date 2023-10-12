package com.dicoding.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.storyapp.api.response.ListStoryItem
import com.dicoding.storyapp.data.Result
import com.dicoding.storyapp.data.UserRepository
import com.dicoding.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean> get() = _refreshStatus
    fun getAllStories(): LiveData<Result<List<ListStoryItem>>> {
        return repository.getStories()
    }

    val stories: LiveData<PagingData<ListStoryItem>> =
        repository.getAllStories().cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun refreshStories() {
        viewModelScope.launch {
            try {
                repository.refreshStories()
                _refreshStatus.value = true
            } catch (e: Exception) {
                _refreshStatus.value = false
            }
        }
    }

}