package com.dicoding.storyapp.ui.signup

import androidx.lifecycle.ViewModel
import com.dicoding.storyapp.data.UserRepository

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    fun register(username: String, email: String, password: String) =
        repository.register(username, email, password)
}