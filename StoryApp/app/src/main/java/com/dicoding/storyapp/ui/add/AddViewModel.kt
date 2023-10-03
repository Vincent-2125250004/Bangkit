package com.dicoding.storyapp.ui.add

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.UserRepository
import java.io.File

class AddViewModel(private val repository: UserRepository) : ViewModel() {

    fun uploadImage(multipartBody: File, description: String) =
        repository.uploadStory(multipartBody, description)
}